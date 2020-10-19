package creos.simsg.api.transformer.json.exporter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import creos.simsg.api.model.*;
import creos.simsg.api.extractor.CableExtractor;
import creos.simsg.api.extractor.EntityExtractor;
import creos.simsg.api.extractor.FuseExtractor;
import creos.simsg.api.loadapproximator.LoadApproximator;
import creos.simsg.api.transformer.Exporter;
import creos.simsg.api.model.*;

import java.util.HashMap;

/**
 * Transform the grid to a Json object to allow exporting the grid into a JSON file. The exported file should
 * respect the schema (cf. resources/sg-schema.json).
 *
 * <strong>WARNING:</strong> The development of this class is now idle. Moreover, it has never been used and tested.
 * If you want to use it, better write test before :)
 *
 */
public class JsonExporter implements Exporter<JSONObject> {
    @Override
    public JSONObject export(SmartGrid grid) {
        return exportWithLoads(grid, null);
    }

    @Override
    public JSONObject exportWithLoads(SmartGrid grid, LoadApproximator<Double> approximator) {
        final var res = new JSONObject();

        final var fuses = new JSONArray();
        final var mapFuses = new HashMap<Fuse, Integer>();
        var index = new int[]{0};
        grid.getSubstations().forEach((Substation substation) -> {
            final var fuseLoads = new HashMap<Fuse, Double>();
            if(approximator != null) {
                fuseLoads.putAll(approximator.getFuseLoads(substation, true));
            }

            FuseExtractor.INSTANCE
                    .getExtracted(substation)
                    .forEach((Fuse fuse) -> {
                        mapFuses.put(fuse, index[0]);

                        final var fuseMap = new HashMap<String, Object>();
                        fuseMap.put("id", index[0] + "");
                        fuseMap.put("name", fuse.getName());

                        final var stateMap = new HashMap<String, Object>();
                        stateMap.put("status", fuse.getStatus().getState().getName());
                        stateMap.put("confidence", fuse.getStatus().confIsClosed());
                        fuseMap.put("state", new JSONObject(stateMap));

                        if(fuseLoads.size() != 0) {
                            final var loads = new JSONArray();

                            final var loadMap = new HashMap<String, Object>();
                            loadMap.put("value", fuseLoads.get(fuse));
                            loadMap.put("confidence", 1);
                            loads.add(new JSONObject(loadMap));

                            fuseMap.put("load", loads);
                        }

                        fuses.add(new JSONObject(fuseMap));

                        index[0]++;
                    });
        });
        res.put("fuses", fuses);

        final var cables = new JSONArray();
        index[0] = 0;
        grid.getSubstations().forEach((Substation substation) -> {
            final var cableLoads = new HashMap<Cable, Double>();
            if(approximator != null) {
                cableLoads.putAll(approximator.getCableLoads(substation));
            }

            CableExtractor.INSTANCE
                    .getExtracted(substation)
                    .forEach((Cable cable) -> {
                        final var cableMap = new HashMap<String, Object>();
                        cableMap.put("id", index[0] + "");

                        final var cableFuses = new JSONArray(2);
                        cableFuses.add(mapFuses.get(cable.getFirstFuse()) + "");
                        cableFuses.add(mapFuses.get(cable.getSecondFuse()) + "");
                        cableMap.put("fuses", cableFuses);

                        final var meters = new JSONArray();
                        cable.getMeters().forEach((Meter meter) -> {
                            final var meterMap = new HashMap<String, Object>();
                            meterMap.put("name", meter.getName());
                            meterMap.put("consumption", meter.getConsumption());
                            meters.add(new JSONObject(meterMap));
                        });
                        cableMap.put("meters", meters);


                        cables.add(new JSONObject(cableMap));

                        index[0]++;
                    });
        });
        res.put("cables", cables);



        final var entities = new JSONArray();
        grid.getSubstations().forEach((Substation substation) -> {
            EntityExtractor.INSTANCE
                    .getExtracted(substation)
                    .forEach((Entity entity) -> {
                        final var entityJson = new HashMap<String, Object>();
                        entityJson.put("name", entity.getName());
                        entityJson.put("type", entity.getClass().getName());

                        final var fusesJson = new JSONArray();
                        entity.getFuses().forEach((Fuse fuse) -> {
                            fusesJson.add(mapFuses.get(fuse) + "");
                        });
                        entityJson.put("fuses", fusesJson);

                        entities.add(new JSONObject(entityJson));
                    });
        });
        res.put("entities", entities);

        return res;
    }

}

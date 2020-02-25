<template lang="pug">
    div
      div
        span.title State 
        .checkbox 
            label(class="switch")
                input(type="checkbox" id="fuseStatus" v-model.boolean="fuses[fuseId].isClosed")
                span(class="slider")
        span(v-if="fuses[fuseId].isClosed" class="stateInfo") (Closed)
        span(v-else class="stateInfo") (Open)
        .confidence
          span Confidence level:
          br
          input(type="range" min="0" max="100" class="range" v-model.number="fuses[fuseId].confidenceLevel")
          input(type="number" min="0" max="100" class="number" v-model.number="fuses[fuseId].confidenceLevel")
          | %
      .line
      div
        span.title Load
        | {{load}}

        
        

</template>

<script>
import { mapState } from 'vuex'

export default {
    props: {
        fuseId: Number,
    },
    computed: {
        load: function() {
            var load = this.fuses[this.fuseId].load;
            return (load === -1)? "To be computed..." : load.toFixed(2) + " A";
        },
        ...mapState({
            fuses: state => state.fuses
        })
    }

}
</script>

<style lang="scss" scoped>
.title {
  font-weight: bold;
  margin: auto;
  display: block;
}


.line {
  width: 95%;
  margin-left: auto;
  margin-right: auto;
  margin-top: 10px;
  margin-bottom: 10px;
  height: 2px;
  background-color: grey;
  display: block;
}
.confidence {
  .range {
    width: 65%;
    margin-right: 5%;
    position: relative;
    top: 5px;
  }

  //FF only
  .range[type="range"]::-moz-range-progress {
    background-color: $link-color; 
  }
  .range[type="range"]::-moz-range-track {  
    background-color: lightgray;
  }

  .number {
    max-width: 55px;
  }
}

.stateInfo {
    display: inline;
    position: relative;
    left: 40px;
    font-size: 0.9em;
    font-style: italic;
}

.checkbox {
    display: inline;
    position: relative;
    top: 6px;
    left: 10px;
}

.switch {
  position: relative;
  display: inline-block;
  width: 30px;
  height: 16px;
}

.switch input { 
  opacity: 0;
  width: 0;
  height: 0;
}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #ccc;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 12px;
  width: 12px;
  left: 2px;
  bottom: 2px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color: $link-color;
}

input:focus + .slider {
    left: 50px;
    box-shadow: 0 0 1px $link-color;
}

input:checked + .slider:before {
  -webkit-transform: translateX(14px);
  -ms-transform: translateX(14px);
  transform: translateX(14px);
}



/* input {
    width: 15%;
    margin-left: 20px; 
} */
</style>
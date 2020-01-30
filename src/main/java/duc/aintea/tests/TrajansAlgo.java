package duc.aintea.tests;

import java.util.*;

public class TrajansAlgo {

    static class Node {
        int data;
        Node[] neighbours;

        Node(int data) {
            this.data = data;
            this.neighbours = new Node[0];
        }

        public void addneighbours(Node... neighbours) {
            this.neighbours = neighbours;
        }

        public Node[] getNeighbours() {
            return neighbours;
        }

        @Override
        public String toString() {
            return "Node(data=" + data + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Node)) {
                return true;
            }
            return this.data == ((Node) obj).data;
        }

        @Override
        public int hashCode() {
            return data;
        }
    }

    private int index = 0;
    private Deque<Node> circle = new ArrayDeque<>();
    private Map<Node, Integer> idx = new HashMap<>();
    private Map<Node, Integer> lowLink = new HashMap<>();

    public Node[] rec_getCircleFrom(Node start) {
        idx.put(start, index);
        lowLink.put(start, index);
        index++;
        circle.push(start);

        for (var n : start.neighbours) {
            if (!idx.containsKey(n)) {
                rec_getCircleFrom(n);
                lowLink.put(start, Math.min(lowLink.get(start), lowLink.get(n)));
            } else if (circle.contains(n)) {
                lowLink.put(start, Math.min(lowLink.get(start), lowLink.get(n)));
            }
        }

        if (lowLink.get(start).equals(idx.get(start))) {
            Node w;
            var counter = 0;
            var res = new ArrayList<Node>();
            do {
                w = circle.pop();
                counter++;
                res.add(w);
            } while (!w.equals(start));
            if (counter > 1) {
                return res.toArray(new Node[0]);
            }
        }

        return new Node[0];
    }


    public static void main(String[] args) {
        /*var n1 = new Node(1);
        var n2 = new Node(2);
        var n3 = new Node(3);
        var n4 = new Node(4);
        var n5 = new Node(5);
        var n6 = new Node(6);
        var n7 = new Node(7);
        var n8 = new Node(8);
        var n9 = new Node(9);
        var n10 = new Node(10);
        var n11 = new Node(11);
//        var n12 = new Node(12);
//        var n13 = new Node(13);
//        var n14 = new Node(14);
//        var n15 = new Node(15);

        n1.addneighbours(n2, n7, n4);
        n3.addneighbours(n1);
        n4.addneighbours(n5, n6, n3);
        n7.addneighbours(n1, n8, n10);
        n8.addneighbours(n9);
        n10.addneighbours(n11);
        n11.addneighbours(n1);
//        n12.addneighbours(n4);
//        n13.addneighbours(n12);
//        n15.addneighbours(n13, n14);

        var nodes = new Node[]{n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11*//*, n12, n13, n14, n15*//*};*/

        var n1 = new Node(1);
        var n2 = new Node(2);
        var n3 = new Node(3);
        var n4 = new Node(4);

        n1.addneighbours(n2, n3);
        n2.addneighbours(n4, n3);
        n3.addneighbours(n1);


        //for (var n : nodes) {
            var algo = new TrajansAlgo();
            var n = n3;
            System.out.println("[" + n + "]");
            System.out.println("\t" + Arrays.toString(algo.rec_getCircleFrom(n)));
        //}


    }


}

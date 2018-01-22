package chapter8;

/**
 * Created by intellij IDEA
 * Author:Raven
 * Date:2018/1/22
 * Time:11:02
 */
public class DisjSets {
    private int[] s;

    /**
     * Construct the disjoint sets object.
     *
     * @param numElements the initial number of disjoint sets.
     */
    public DisjSets(int numElements) {
        s = new int[numElements];
        for (int i = 0; i < s.length; i++) {
            s[i] = -1;
        }
    }

    /**
     * Union two disjoint sets using the height heuristic.
     * For simplicity, we assume root1 and root2 are distinct
     * and represent set names.
     *
     * @param root1 the root of set 1.
     * @param root2 the root of set 2.
     */
    public void union(int root1, int root2) {
        //root2 is deeper
        if (s[root2] < s[root1]) {
            //make root2 new root
            s[root1] = root2;
        } else {
            if (s[root1] == s[root2]) {
                //update height if same
                s[root1]--;
            }
            //make root1 new root
            s[root2] = root1;
        }
    }

    /**
     * Perform a find with path compression.
     * Error checks omitted again for simplicity.
     *
     * @param x the element being searched for.
     * @return the set containing x.
     */
    public int find(int x) {
        if (s[x] < 0) {
            return x;
        } else {
            return find(s[x]);
        }
    }

    // Test main; all finds on same output line should be identical
    public static void main(String[] args) {
        int numElements = 128;
        int numInSameSet = 16;

        DisjSets ds = new DisjSets(numElements);
        int set1, set2;
        for (int i = 1; i < numInSameSet; i *= 2) {
            for (int j = 0; j + i < numElements; j += 2 * i) {
                set1 = ds.find(j);
                set2 = ds.find(j + i);
                ds.union(set1, set2);
            }
        }
        for (int i = 0; i < numElements; i++) {
            System.out.print(ds.find(i) + "*");
            if (i % numInSameSet == numInSameSet - 1) {
                System.out.println();
            }
        }
        System.out.println();

    }
}

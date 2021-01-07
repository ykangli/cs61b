package paractice;

public class QuickFindDS implements DisjointSets {
    private int[] id;

    /* Θ(N) */
    public QuickFindDS(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    /* need to iterate through the array => Θ(N) */
    @Override
    public void connect(int p, int q) {
        int pid = p;
        int qid = q;
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) {
                id[i] =qid;
            }
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return (id[q] == id[p]);
    }
}

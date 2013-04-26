package util;

public class Pair<K,V> {
    public final K fst;
    public final V snd;

    public Pair(K f, V s) {
	fst = f;
	snd = s;
    }
}
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;


public class MyTrie extends AbstractSet<String> {

	boolean isWord;
	int size;
	public static int letters = 26; 
	MyTrie[] children;
	static String alphabet = "abcdefghijklmnopqrstuvwxyz";

	public MyTrie() {
		size=0;
		children = new MyTrie[26];
		isWord=false;
	}

	public  boolean containsEmptyString() {
		return isWord;
	}

	public boolean contains(String str) {
		str = str.toLowerCase();
		int l = str.length();
		if (l==0) {
			return isWord;
		}
		else {
			char start = str.charAt(0);
			int intStart = alphabet.indexOf(start);
			if (!(children[intStart]==null)) {
				return (children[intStart].contains(str.substring(1)));
				
			}
			else {
				return false;
			}
		}
	}

	public boolean containsPrefix(String prefix) {
		prefix = prefix.toLowerCase();
		int l = prefix.length();
		if (l==0) {
			return true;
		}
		else {
			char start = prefix.charAt(0);
			int intStart = alphabet.indexOf(start);
			if (children[intStart]==null) {
				return false;
			}
			else {
				return (children[intStart].containsPrefix(prefix.substring(1)));
			}
		}
	}

	public boolean add(String string) {
		int l = string.length();
		if (l==0) {
			if (isWord) {
				isWord=true;
				return false;
			}
			else {
				isWord=true;
				size++;
				return true;
			}

		}
		else {
			char start = string.charAt(0);
			int intStart = alphabet.indexOf(start);
			if (children[intStart]==null) {
				children[intStart]= new MyTrie();
			}
			boolean T= children[intStart].add(string.substring(1));
			if (!T) {
				return false;
			}
			else {
				size++;
				return true;
			}
		}
	}

	public boolean isEmpty(){
		if (size()==0){
			return true;
		}
		return false;
	}

	public String toString(){
		return toList().toString();
	}

	private ArrayList<String> toList(){
		ArrayList<String> L = new ArrayList<String>();
		for (int i =0; i<26; i++){
			String str = "";
			String hi = "" + nthLetter(i);
			ArrayList<String> L1 = new ArrayList<String>();
			if (!(children[i]==null)){
				L.addAll(children[i].toList(hi, L1));
			}
		}
		return L;
	}

	public char nthLetter(int i){
		return alphabet.charAt(i);
	}

	private ArrayList<String> toList(String prefix, ArrayList<String> List) {
		if (isWord){
			List.add(prefix);
		}
		for (int i =0; i<26; i++){
			char c = nthLetter(i);
			if (children[i]!=null){
				String str = prefix + c;
				ArrayList<String> L1 = children[i].toList(str, List);
				
			}
		}
		return List;
	}

	@Override
	public Iterator<String> iterator() {
		return toList().iterator();
	}

	@Override
	public int size() {
		return size;
	}

}


package cn.edu.heuet.II.algorithm;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DictionaryAnagram {
	/**
	 * 在一个词典中找出所有的变位词， 例如 pots stop tops互为变位词， 因为一个单词可以通过改变其他单词中字母的顺序得到其他单词。
	 */
	/**
	 * 我们可以把首先遇到的单词，进行排序。 例如pans排序后变为anps，我们现在就把anps作为单词pans的标记，
	 * 然后当在遇到snap，也将anps标记为anps。 也就是将所有只以字母a n p s组成的单词都标记为anps。 遇到的其他单词也已相同的方法进行标记。
	 * 然后再对所有的标记进行排序， 就可以肯容易得到一组变位词。
	 */
	public void dictionaryAnagram(String rFilename, String wFilename) {
		try {
			FileReader fr = new FileReader(rFilename);
			BufferedReader br = new BufferedReader(fr);
			FileOutputStream fos = new FileOutputStream(wFilename);
			OutputStreamWriter osw = new OutputStreamWriter(fos);

			HashSet<String> wordsSet = new HashSet<String>();
			IdentityHashMap<String, String> wordsMap = new IdentityHashMap<String, String>();
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] sArr = line.trim().split(" ");
				for (String word : sArr) {
					wordsSet.add(word);
				}
			}
			br.close();
			fr.close();

			for (String word : wordsSet) {
				char[] sortWordChar = word.toCharArray();
				Arrays.sort(sortWordChar);
				String sortWord = new String(sortWordChar);
				wordsMap.put(sortWord, word);
			}

			ArrayList<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
					wordsMap.entrySet());
			Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
				public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});

			ArrayList<String> temp = new ArrayList<String>();
			for (Entry<String, String> entry : infoIds) {
				if (!temp.contains(entry.getKey())) {
					osw.write("\n");
				}
				temp.add(entry.getKey());
				osw.write(entry.getValue() + "\t");
			}
			osw.write("\n");
			osw.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void dictionaryAnagram2(String rFilename, String wFilename) {
		try {
			FileReader fr = new FileReader(rFilename);
			BufferedReader br = new BufferedReader(fr);
			FileOutputStream fos = new FileOutputStream(wFilename);
			OutputStreamWriter osw = new OutputStreamWriter(fos);

			HashMap<String, String> wordsMap = new HashMap<String, String>();
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] sArr = line.trim().split(" ");
				for (String word : sArr) {
					char[] sortWordChar = word.toCharArray();
					Arrays.sort(sortWordChar);
					String sortWord = new String(sortWordChar);
					wordsMap.put(word, sortWord);
				}
			}
			br.close();
			fr.close();

			ArrayList<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
					wordsMap.entrySet());
			Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
				public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
					return (o1.getValue()).compareTo(o2.getValue());
				}
			});

			ArrayList<String> temp = new ArrayList<String>();
			for (Entry<String, String> entry : infoIds) {
				if (!temp.contains(entry.getValue())) {
					osw.write("\n");
				}
				temp.add(entry.getValue());
				osw.write(entry.getKey() + "\t");
			}
			osw.write("\n");
			osw.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		DictionaryAnagram da = new DictionaryAnagram();
		da.dictionaryAnagram("D:\\words.txt", "D:\\words2.txt");
		da.dictionaryAnagram2("D:\\words.txt", "D:\\words3.txt");
		System.out.println("Done!");
	}

}
package coding_test;

import java.util.*;

public class Main {
    static HashMap<String, ArrayList<Integer>> database = new HashMap<>();

    public static void main(String[] args) {
        String[] info = {"java backend junior pizza 150","python frontend senior chicken 210","python frontend senior chicken 150","cpp backend senior pizza 260","java backend junior chicken 80","python backend senior chicken 50"};
        String[] queries = {"java and backend and junior and pizza 100","python and frontend and senior and chicken 200","cpp and - and senior and pizza 250","- and backend and senior and - 150","- and - and - and chicken 100","- and - and - and - 150"};
        for (int i : solution(info, queries)) {
            System.out.println(i);
        }
    }

    public static int[] solution(String[] info, String[] queries) {
        int[] answer = new int[queries.length];

        for (String s : info)
            insert("", 0, s.split(" "));

        // 모든 키들을 sort하기.
        sortDatabase();

        // sort한 친구들을 쿼리로 꺼내오기
        for(int j = 0; j < queries.length; j++){
            queries[j] = queries[j].replaceAll(" and ", "");
            String[] keyElementList = queries[j].split(" ");
            String key = keyElementList[0];
            int score = Integer.parseInt(keyElementList[keyElementList.length-1]);

            answer[j] = select(key, score);
        }

        return answer;
    }

    private static int select(String query, int score) {
        if(!database.containsKey(query)) return 0;
        ArrayList<Integer> scoreList = database.get(query);
        int start = 0, end = scoreList.size()-1;

        while(start <= end) {
            int mid = (start+end)/2;

            if(scoreList.get(mid) < score)
                start = mid+1;
            else
                end = mid-1;
        }

        return scoreList.size()-start;
    }

    private static void sortDatabase() {
        for (String key : database.keySet()) {
            if(database.get(key).isEmpty()) continue;
            ArrayList<Integer> scoreList = database.get(key);
            Collections.sort(scoreList);
        }
    }

    private static void insert(String key, int depth, String[] info) {
        if(depth == 4) {
            int score = Integer.parseInt(info[4]);
            if(!database.containsKey(key)) {
                ArrayList<Integer> scoreList = new ArrayList<>();
                database.put(key, scoreList);
            }
            database.get(key).add(score);
            return;
        }

        insert(key+"-", depth+1, info);
        insert(key+info[depth], depth+1, info);
    }
}
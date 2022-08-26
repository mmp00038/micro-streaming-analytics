package com.msa.microstreaminganalytics;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.*;

public class CalculateStatistics {

    public static Statistics getJsonStatistics(){

        System.out.println("GetJsonStatistics");
        double mean = 0.0;
        double median = 0.0;
        double mode = 0.0;
        double standardDeviation = 0.0;
        double[] quartiles = new double[3];
        double min = 0.0;
        double max = 0.0;

        try(FileReader reader = new FileReader("members.json")){
            //read json file

            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(reader);

            //Get members
            JSONArray list = (JSONArray) object.get("members");

            double sumAge = 0.0;
            List<Double> arrayAge = new ArrayList<>();

            // take each value from the json array separately
            Iterator i = list.iterator();
            while (i.hasNext()) {
                JSONObject innerObj = (JSONObject) i.next();
                Double age = Double.parseDouble(innerObj.get("age").toString());
                sumAge += age;
                arrayAge.add(age);
            }

            mean = sumAge/list.size();
            median = getMedian(arrayAge);
            mode = getMode(arrayAge);
            standardDeviation = getStandardDeviation(mean, arrayAge);
            quartiles = getQuartiles(arrayAge);
            min = Collections.min(arrayAge);
            max = Collections.max(arrayAge);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return new Statistics(mean,median,mode,standardDeviation,quartiles,max,min);
    }

    private static Double getMedian(List<Double> arrayAge) {
        Collections.sort(arrayAge);
        System.out.println("Array sort is: " + arrayAge);

        double median;
        int half = arrayAge.size() / 2;

        //If the length is even, the middle ones must be averaged.
        if (arrayAge.size() % 2 == 0) {
            median = (arrayAge.get(half - 1) + arrayAge.get(half) / 2);
        } else {
            median = arrayAge.get(half);
        }

        return median;
    }

    private static double getMode(List<Double> arrayAge) {
        Map<Double, Integer> m = new HashMap();

        for (double age: arrayAge){
            if (m.containsKey(age))
                m.put(age,m.get(age)+1);
            else
                m.put(age,1);
        }

        int repetitions = 0;
        List<Double> mode = new ArrayList();

        Iterator iter = m.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<Double, Integer> e = (Map.Entry<Double, Integer>) iter.next();

            if (e.getValue() > repetitions) {
                mode.clear();
                mode.add(e.getKey());
                repetitions = e.getValue();
            } else if (e.getValue() == repetitions) {
                mode.add(e.getKey());
            }
        }

        return mode.get(0);
    }

    private static double getStandardDeviation(double mean, List<Double> arrayAge) {

        double variance = 0.0;

        for(int i = 0; i < arrayAge.size(); i++){
            double range;
            range = Math.pow(arrayAge.get(i) - mean, 2f);
            variance = variance + range;
        }

        variance = variance/10f;

        return Math.sqrt(variance);

    }

    public static double[] getQuartiles(List<Double> arrayAge) {
        double quartiles[] = new double[3];

        for (int quartileType = 1; quartileType < 4; quartileType++) {
            float size = arrayAge.size() + 1;
            double quartile;
            float newArraySize = (size * ((float) (quartileType) * 25 / 100)) - 1;
            Collections.sort(arrayAge);
            if (newArraySize % 1 == 0) {
                quartile = arrayAge.get((int) (newArraySize));
            } else {
                int newArraySize1 = (int) (newArraySize);
                quartile = (arrayAge.get(newArraySize1) + arrayAge.get(newArraySize1 + 1)) / 2;
            }
            quartiles[quartileType - 1] =  quartile;
            System.out.println("Quartile " + quartileType + " is: " + quartiles[quartileType - 1]);
        }
        return quartiles;
    }

}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class parserMain {


    private final static String separator = File.separator;
    private final static String FPATH = System.getProperty("user.dir") + separator + "src" +
            separator + "main" + separator + "resources" + separator + "lng.txt";


    private static Set<TextEntity> uniqStr = new HashSet<>();
    private static Map<TEntity, ArrayList<TextEntity>> containers = new HashMap<>();
    private static Map<Integer, ArrayList<Group>> foundGroups = new TreeMap<>(Collections.reverseOrder());

    private static int countGroup = 0;



    public static void main(String[] args) throws Exception {


        long timeCode = System.currentTimeMillis();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FPATH))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                textFilter(line.replace("\"", "").split(";", -1));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        //три метода
        clearGroup();
        finalGrouping();
        print();
    }

    private static void filterData(String[] values) {
        if(values.length == 3) {
            TextEntity multiEntity = new TextEntity(values);
            if(!uniqStr.contains(multiEntity) && multiEntity.marker()) {
                uniqStr.add(multiEntity);
                primaryGrouping(multiEntity);
            }
        }
    }

    private static void primaryGrouping(TextEntity multiEntity) {
        for (TEntity entity : multiEntity.getMarker()) {
            ArrayList<TextEntity> v = containers.getOrDefault(entity, new ArrayList<>());
            v.add(multiEntity);
            containers.put(entity, v);
        }
    }


    private static void clearGroup(){
        Map<TEntity, ArrayList<TextEntity>> NContains = new HashMap<>();
        for (Map.Entry<TEntity,ArrayList<TextEntity>> entry : containers.entrySet()){
            if (entry.getValue().size()>1){
                NContains.put(entry.getKey(), entry.getValue());
            }

        }
        containers = NContains;
    }

    private static void finalGrouping() {
        additionalGrouping();

        for (Map.Entry<TEntity, ArrayList<TextEntity>> entry : containers.entrySet()) {
            ArrayList<Group> v = foundGroups.getOrDefault(entry.getValue().size(), new ArrayList<>());
            Group group = new Group(entry.getValue());
            v.add(group);
            foundGroups.put(group.size(), v);
            countGroup++;
        }
    }

    private static void additionalGrouping() {
        Map<TextEntity, Integer> multiEntityCount = new HashMap<>();

        for (Map.Entry<TEntity,  ArrayList<TextEntity>> entry : containers.entrySet()) {
            ArrayList<TextEntity> entities = entry.getValue();
            for(TextEntity multiEntity : entities) {
                int value = multiEntityCount.getOrDefault(multiEntity, 0);
                multiEntityCount.put(multiEntity, value + 1);
            }
        }

        List<Set<TextEntity>> multiEntitySubGroup = new ArrayList<>();
        for (Map.Entry<TextEntity, Integer> entry : multiEntityCount.entrySet()) {
            if(entry.getValue() > 1) {
                Set<TextEntity> set = new HashSet<>();
                for (TEntity entity : entry.getKey().getMarker()) {
                    if(containers.containsKey(entity)) {
                        set.addAll(containers.remove(entity));
                    }
                }
                if(set.size() > 1)
                    multiEntitySubGroup.add(set);
            }
        }

        for (Set<TextEntity> subGroup : multiEntitySubGroup) {
            ArrayList<Group> v = foundGroups.getOrDefault(subGroup.size(), new ArrayList<>());
            Group group = new Group(new ArrayList<>(subGroup));
            v.add(group);
            foundGroups.put(group.size(), v);
            countGroup++;
        }
    }


    private static void textFilter(String[] v){
        if (v.length == 3)
        {
            TextEntity entity = new TextEntity(v);
            if (!uniqStr.contains(entity) && entity.marker()){
                uniqStr.add(entity);
                groupingOperation(entity);
            }

        }
    }

    private static void groupingOperation(TextEntity textEntity){
        for (TEntity tEntity : textEntity.getMarker()){
            ArrayList<TextEntity> v = containers.getOrDefault(tEntity, new ArrayList<>());
            v.add(textEntity);
            containers.put(tEntity, v);

        }
    }

    public static void print() {
        System.out.println("Кол-во групп " + countGroup);
        int count = 1;
        for (Map.Entry<Integer, ArrayList<Group>> entry : foundGroups.entrySet()) {
            for(Group group : entry.getValue()) {
                System.out.println("Группа " + count + "\n");

                for (TextEntity st : group.getGroup()){

                    System.out.println(st.toString()+"\n");
                }

                count++;
            }
        }
    }



}

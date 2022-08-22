import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class TextEntity {
    private List<TEntity> tEntityList = new ArrayList<>();

    public TextEntity(String... strings){
        for (Column column : Column.values()){
            tEntityList.add(new TEntity(column, strings[column.getValue()]));
        }
    }



    public boolean marker() {
        for (TEntity tEntity : tEntityList){
            if (tEntity.marker()){
                return true;
            }
        }
        return false;
    }

    public List<TEntity> getMarker() {
        return tEntityList.stream().filter(TEntity::marker).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextEntity that = (TextEntity) o;
        return tEntityList.equals(that.tEntityList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tEntityList);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(";");
        for (TEntity entity : tEntityList)
            joiner.add(entity.toString());
        return joiner.toString();
    }
}

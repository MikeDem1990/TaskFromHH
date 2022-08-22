import java.util.Objects;

public class TEntity {
    private Column column;
    private String number;
    private boolean legit;

    public TEntity(Column column, String number) {
        this.column = column;
        this.number = number;
        if (number.isEmpty()){
            legit = false;
        } else {legit = true;}
    }

    public boolean marker(){
        return legit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TEntity entity = (TEntity) o;
        return column == entity.column &&
                number.equals(entity.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, number);
    }

    @Override
    public String toString() {
        return "\"" + number + "\"";
    }
}

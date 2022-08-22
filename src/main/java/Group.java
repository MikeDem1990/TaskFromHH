import java.util.List;
import java.util.Objects;

public class Group {
    private List<TextEntity> listGroup;

    public Group(List<TextEntity> group) {
        this.listGroup = group;
    }

    public List<TextEntity> getGroup() {
        return listGroup;
    }

    public int size() {
        return listGroup.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return listGroup.equals(group.listGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listGroup);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (TextEntity multiEntity : listGroup) {
            str.append(multiEntity + "\n");
        }
        return str.toString();
    }
}

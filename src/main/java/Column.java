public enum Column {

    ColFirst(0),
    ColSecond(1),
    ColThird(2);
    private Integer value;

    public Integer getValue(){
        return value;
    }

    Column(Integer value){
        this.value = value;
    }
}

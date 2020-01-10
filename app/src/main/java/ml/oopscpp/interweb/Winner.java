package ml.oopscpp.interweb;


class Winner {

    private String image;
    private String name;
    private String eventName;

    Winner(){

    }

    Winner(String image, String name, String event){
        this.image = image;
        this.name = name;
        this.eventName = event;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getEventName() {
        return eventName;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }


}
package tech.morbit.Quality;

import tech.morbit.Exception.InvalidInputException;
import tech.morbit.Tools.TypeHelper;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author Morbit
 * @version 0.1.9
 *
 * RECENTLY:
 * Added an toString
 *
 * PURPOSE:
 * Mother of the other qualities and helps regulate the handling of these
 * via defining things for the rest, while the rest gain extra handlers, as the data
 * of the different qualities is stored twice, once in the values list as in here
 * as well as there respective properties in their own classes.
 *
 */


public abstract class Quality {

    protected int valueCount;

    protected static final Set<Class<?>> VALID_TYPES = Set.of(
            Boolean.class,
            Integer.class,
            Double.class,
            String.class,
            Quality.class
    );

    protected String name;
    protected ArrayList<Object> values;
    protected ArrayList<Tag> tags = new ArrayList<>();

    TypeHelper typeHelper;

    public <T> Quality(
            String name)  {
        this.name = name;


    }

    public <T> Quality(
            String name,
            ArrayList<T> values,
            ArrayList<Tag> tags) throws InvalidInputException {
                this(name);
                this.tags.addAll(tags);
    }

    public void setName(String name){this.name = name;}
    public String getName(){return this.name;}


    public Class<? extends Quality> getQuality(){return this.getClass();}

    public ArrayList<Object> getValues(){return this.values;}
    public Class getTypeOfValues(){
        return this.getValues().get(0).getClass();
    }

    public <T> void setValues(ArrayList<T> values) throws InvalidInputException {
        if(values.size() == this.valueCount){
            this.values = (ArrayList<Object>) values;
        } else{ throw new InvalidInputException();}
    }

    //Tagging
    public ArrayList<Tag> getTags(){return this.tags;}
    public ArrayList<Tag> getTagsByFunctionality(boolean functionality){
        ArrayList<Tag> tagFunc = new ArrayList<>();

        for (int i = 0; i < this.tags.toArray().length; i++) {
            if(functionality == this.tags.get(i).getFunctional()){
                tagFunc.add(this.tags.get(i));
            }
        }
        return tagFunc;
    }

    public void setTags(ArrayList<Tag> tags){this.tags = tags;}
    public void addTag(Tag tag){this.tags.add(tag);}
    public void addTags(ArrayList<Tag> tags){this.tags.addAll(tags);}

    public void removeTag(Tag tag){this.tags.remove(tag);}
    public void removeTags(ArrayList<Tag> tags){
        for(Tag tag : tags){
            this.tags.remove(tag);
        }
    }

    //Strings
    public String getValuesAsInputString(){
        String output = "";
        for(Object value : this.values){
            output += value.toString() +";";
        }
        output = output.substring(0,output.length()-1);
        return output;
    }
    @Override
    public String toString(){return String.format("%s: %s - %s",
            this.name,
            getTypeOfValues().getSimpleName(),
            this.getClass().getSimpleName());}

    public static <T extends Quality> ArrayList<Class<? extends Quality>> getClassAndChildren(){
        ArrayList<Class<? extends  Quality>> q = new ArrayList<>();
        q.add(Quality.class);
        //q
        return q;
    }
}

package mt.taxm.app;

import java.io.Serializable;

public class Pair implements Serializable{
    private static final long serialVersionUID = 1L;
    public String key;
    public String value;
    public Pair(){}
    public Pair(String key, String value) {
        this.key = key;
        this.value = value;
    }
    
    public Pair(String key) {
        this.key = key;
    }
    
    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }
        if(!(o instanceof Pair)){
            return false;
        }
        if(o == this){
            return true;
        }
        Pair p = (Pair)o;
        return key.equals(p.key);
    }
    
    @Override
    public int hashCode() {
        return 31 + key.hashCode();
    }
    
    
    
    
    
}

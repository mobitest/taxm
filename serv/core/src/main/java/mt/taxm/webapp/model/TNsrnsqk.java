package mt.taxm.webapp.model;
// Generated 2013-8-5 20:50:25 by Hibernate Tools 3.2.2.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * TNsrnsqk generated by hbm2java
 */
@Entity
@Table(name="T_NSRNSQK"
)
@NamedQueries({
	@NamedQuery(
	name = "nsByBm",
	query = "select s FROM TNsrnsqk s where s.id.nsrbm like :bm order by s.id.sfssqQsrq desc"
	)
})

public class TNsrnsqk  implements java.io.Serializable {


     private TNsrnsqkId id;

    public TNsrnsqk() {
    }

    public TNsrnsqk(TNsrnsqkId id) {
       this.id = id;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="nsrnbm", column=@Column(name="NSRNBM", nullable=false, precision=10, scale=0) ), 
        @AttributeOverride(name="nsrbm", column=@Column(name="NSRBM", nullable=false, length=20) ), 
        @AttributeOverride(name="sz", column=@Column(name="SZ", length=100) ), 
        @AttributeOverride(name="sfssqQsrq", column=@Column(name="SFSSQ_QSRQ", length=7) ), 
        @AttributeOverride(name="sfssqZzrq", column=@Column(name="SFSSQ_ZZRQ", length=7) ), 
        @AttributeOverride(name="jkQx", column=@Column(name="JK_QX", length=100) ), 
        @AttributeOverride(name="yzsfJe", column=@Column(name="YZSF_JE", precision=18) ) } )
    public TNsrnsqkId getId() {
        return this.id;
    }
    
    public void setId(TNsrnsqkId id) {
        this.id = id;
    }




}



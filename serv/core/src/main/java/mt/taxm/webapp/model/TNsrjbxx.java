package mt.taxm.webapp.model;
// Generated 2013-8-5 20:50:25 by Hibernate Tools 3.2.2.GA


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * TNsrjbxx generated by hbm2java
 */
@Entity
@Table(name="T_NSRJBXX"
    ,schema="ZGPAD"
)
@NamedQueries({
	@NamedQuery(
	name = "jbxxByKeyword",
	query = "select s FROM TNsrjbxx s where s.nsrMc like :keyword or s.nsrbm like :keyword"
	)
})
@NamedNativeQuery(name = "jbxxByKeyword2", query = "select * from TNsrjbxx where s.nsrbm like ? and rownum<30", resultClass =TNsrjbxx.class) 

public class TNsrjbxx  implements java.io.Serializable {


     private long nsrnbm;
     private String nsrMc;
     private String nsrbm;
     private String zcDz;
     private String sjjyDz;
     private String zclx;
     private String xzjd;
     private String xydjJb;
     private String fddbrMc;
     private String lxdhDh;
     private String frsjhm;
     private String cwfzrMc;
     private String bsyMc;
     private String kglx;
     private String swdjlb;
     private String swdjzh;
     private String djzt;
     private String lsgx;
     private String hy;
     private String gljg;
     private String djjg;
     private String zgy;
     private Date yxQsrq;
     private Date djRq;
     private String jyfwzy;

    public TNsrjbxx() {
    }

	
    public TNsrjbxx(long nsrnbm, String nsrMc, String nsrbm, String swdjlb, String djzt, String lsgx, String hy) {
        this.nsrnbm = nsrnbm;
        this.nsrMc = nsrMc;
        this.nsrbm = nsrbm;
        this.swdjlb = swdjlb;
        this.djzt = djzt;
        this.lsgx = lsgx;
        this.hy = hy;
    }
    public TNsrjbxx(long nsrnbm, String nsrMc, String nsrbm, String zcDz, String sjjyDz, String zclx, String xzjd, String xydjJb, String fddbrMc, String lxdhDh, String frsjhm, String cwfzrMc, String bsyMc, String kglx, String swdjlb, String swdjzh, String djzt, String lsgx, String hy, String gljg, String djjg, String zgy, Date yxQsrq, Date djRq, String jyfwzy) {
       this.nsrnbm = nsrnbm;
       this.nsrMc = nsrMc;
       this.nsrbm = nsrbm;
       this.zcDz = zcDz;
       this.sjjyDz = sjjyDz;
       this.zclx = zclx;
       this.xzjd = xzjd;
       this.xydjJb = xydjJb;
       this.fddbrMc = fddbrMc;
       this.lxdhDh = lxdhDh;
       this.frsjhm = frsjhm;
       this.cwfzrMc = cwfzrMc;
       this.bsyMc = bsyMc;
       this.kglx = kglx;
       this.swdjlb = swdjlb;
       this.swdjzh = swdjzh;
       this.djzt = djzt;
       this.lsgx = lsgx;
       this.hy = hy;
       this.gljg = gljg;
       this.djjg = djjg;
       this.zgy = zgy;
       this.yxQsrq = yxQsrq;
       this.djRq = djRq;
       this.jyfwzy = jyfwzy;
    }
   
     @Id 
    
    @Column(name="NSRNBM", unique=true, nullable=false, precision=10, scale=0)
    public long getNsrnbm() {
        return this.nsrnbm;
    }
    
    public void setNsrnbm(long nsrnbm) {
        this.nsrnbm = nsrnbm;
    }
    
    @Column(name="NSR_MC", nullable=false, length=200)
    public String getNsrMc() {
        return this.nsrMc;
    }
    
    public void setNsrMc(String nsrMc) {
        this.nsrMc = nsrMc;
    }
    
    @Column(name="NSRBM", nullable=false, length=20)
    public String getNsrbm() {
        return this.nsrbm;
    }
    
    public void setNsrbm(String nsrbm) {
        this.nsrbm = nsrbm;
    }
    
    @Column(name="ZC_DZ", length=200)
    public String getZcDz() {
        return this.zcDz;
    }
    
    public void setZcDz(String zcDz) {
        this.zcDz = zcDz;
    }
    
    @Column(name="SJJY_DZ", length=200)
    public String getSjjyDz() {
        return this.sjjyDz;
    }
    
    public void setSjjyDz(String sjjyDz) {
        this.sjjyDz = sjjyDz;
    }
    
    @Column(name="ZCLX", length=50)
    public String getZclx() {
        return this.zclx;
    }
    
    public void setZclx(String zclx) {
        this.zclx = zclx;
    }
    
    @Column(name="XZJD", length=200)
    public String getXzjd() {
        return this.xzjd;
    }
    
    public void setXzjd(String xzjd) {
        this.xzjd = xzjd;
    }
    
    @Column(name="XYDJ_JB", length=2)
    public String getXydjJb() {
        return this.xydjJb;
    }
    
    public void setXydjJb(String xydjJb) {
        this.xydjJb = xydjJb;
    }
    
    @Column(name="FDDBR_MC", length=80)
    public String getFddbrMc() {
        return this.fddbrMc;
    }
    
    public void setFddbrMc(String fddbrMc) {
        this.fddbrMc = fddbrMc;
    }
    
    @Column(name="LXDH_DH", length=30)
    public String getLxdhDh() {
        return this.lxdhDh;
    }
    
    public void setLxdhDh(String lxdhDh) {
        this.lxdhDh = lxdhDh;
    }
    
    @Column(name="FRSJHM", length=30)
    public String getFrsjhm() {
        return this.frsjhm;
    }
    
    public void setFrsjhm(String frsjhm) {
        this.frsjhm = frsjhm;
    }
    
    @Column(name="CWFZR_MC", length=80)
    public String getCwfzrMc() {
        return this.cwfzrMc;
    }
    
    public void setCwfzrMc(String cwfzrMc) {
        this.cwfzrMc = cwfzrMc;
    }
    
    @Column(name="BSY_MC", length=80)
    public String getBsyMc() {
        return this.bsyMc;
    }
    
    public void setBsyMc(String bsyMc) {
        this.bsyMc = bsyMc;
    }
    
    @Column(name="KGLX", length=30)
    public String getKglx() {
        return this.kglx;
    }
    
    public void setKglx(String kglx) {
        this.kglx = kglx;
    }
    
    @Column(name="SWDJLB", nullable=false, length=100)
    public String getSwdjlb() {
        return this.swdjlb;
    }
    
    public void setSwdjlb(String swdjlb) {
        this.swdjlb = swdjlb;
    }
    
    @Column(name="SWDJZH", length=30)
    public String getSwdjzh() {
        return this.swdjzh;
    }
    
    public void setSwdjzh(String swdjzh) {
        this.swdjzh = swdjzh;
    }
    
    @Column(name="DJZT", nullable=false, length=60)
    public String getDjzt() {
        return this.djzt;
    }
    
    public void setDjzt(String djzt) {
        this.djzt = djzt;
    }
    
    @Column(name="LSGX", nullable=false, length=100)
    public String getLsgx() {
        return this.lsgx;
    }
    
    public void setLsgx(String lsgx) {
        this.lsgx = lsgx;
    }
    
    @Column(name="HY", nullable=false, length=80)
    public String getHy() {
        return this.hy;
    }
    
    public void setHy(String hy) {
        this.hy = hy;
    }
    
    @Column(name="GLJG", length=80)
    public String getGljg() {
        return this.gljg;
    }
    
    public void setGljg(String gljg) {
        this.gljg = gljg;
    }
    
    @Column(name="DJJG", length=80)
    public String getDjjg() {
        return this.djjg;
    }
    
    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }
    
    @Column(name="ZGY", length=30)
    public String getZgy() {
        return this.zgy;
    }
    
    public void setZgy(String zgy) {
        this.zgy = zgy;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="YX_QSRQ", length=7)
    public Date getYxQsrq() {
        return this.yxQsrq;
    }
    
    public void setYxQsrq(Date yxQsrq) {
        this.yxQsrq = yxQsrq;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DJ_RQ", length=7)
    public Date getDjRq() {
        return this.djRq;
    }
    
    public void setDjRq(Date djRq) {
        this.djRq = djRq;
    }
    
    @Column(name="JYFWZY", length=4000)
    public String getJyfwzy() {
        return this.jyfwzy;
    }
    
    public void setJyfwzy(String jyfwzy) {
        this.jyfwzy = jyfwzy;
    }




}



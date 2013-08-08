package mt.taxm.webapp.model;
// Generated 2013-8-5 20:50:25 by Hibernate Tools 3.2.2.GA


import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TSbfjfqkId generated by hbm2java
 */
@Embeddable
public class TSbfjfqkId  implements java.io.Serializable {


     private String nsrbm;
     private String nsrMc;
     private String gljg;
     private String zsxm;
     private String zspm;
     private String sbfs;
     private String sksx1;
     private String sksx2;
     private String sfsx;
     private String ssq;
     private String sum;

    public TSbfjfqkId() {
    }

	
    public TSbfjfqkId(String nsrbm, String nsrMc, String zsxm, String zspm) {
        this.nsrbm = nsrbm;
        this.nsrMc = nsrMc;
        this.zsxm = zsxm;
        this.zspm = zspm;
    }
    public TSbfjfqkId(String nsrbm, String nsrMc, String gljg, String zsxm, String zspm, String sbfs, String sksx1, String sksx2, String sfsx, String ssq, String sum) {
       this.nsrbm = nsrbm;
       this.nsrMc = nsrMc;
       this.gljg = gljg;
       this.zsxm = zsxm;
       this.zspm = zspm;
       this.sbfs = sbfs;
       this.sksx1 = sksx1;
       this.sksx2 = sksx2;
       this.sfsx = sfsx;
       this.ssq = ssq;
       this.sum = sum;
    }
   

    @Column(name="NSRBM", nullable=false, length=20)
    public String getNsrbm() {
        return this.nsrbm;
    }
    
    public void setNsrbm(String nsrbm) {
        this.nsrbm = nsrbm;
    }

    @Column(name="NSR_MC", nullable=false, length=200)
    public String getNsrMc() {
        return this.nsrMc;
    }
    
    public void setNsrMc(String nsrMc) {
        this.nsrMc = nsrMc;
    }

    @Column(name="GLJG", length=100)
    public String getGljg() {
        return this.gljg;
    }
    
    public void setGljg(String gljg) {
        this.gljg = gljg;
    }

    @Column(name="ZSXM", nullable=false, length=60)
    public String getZsxm() {
        return this.zsxm;
    }
    
    public void setZsxm(String zsxm) {
        this.zsxm = zsxm;
    }

    @Column(name="ZSPM", nullable=false, length=60)
    public String getZspm() {
        return this.zspm;
    }
    
    public void setZspm(String zspm) {
        this.zspm = zspm;
    }

    @Column(name="SBFS", length=20)
    public String getSbfs() {
        return this.sbfs;
    }
    
    public void setSbfs(String sbfs) {
        this.sbfs = sbfs;
    }

    @Column(name="SKSX1", length=20)
    public String getSksx1() {
        return this.sksx1;
    }
    
    public void setSksx1(String sksx1) {
        this.sksx1 = sksx1;
    }

    @Column(name="SKSX2", length=20)
    public String getSksx2() {
        return this.sksx2;
    }
    
    public void setSksx2(String sksx2) {
        this.sksx2 = sksx2;
    }

    @Column(name="SFSX", length=20)
    public String getSfsx() {
        return this.sfsx;
    }
    
    public void setSfsx(String sfsx) {
        this.sfsx = sfsx;
    }

    @Column(name="SSQ", length=20)
    public String getSsq() {
        return this.ssq;
    }
    
    public void setSsq(String ssq) {
        this.ssq = ssq;
    }

    @Column(name="SUM", length=20)
    public String getSum() {
        return this.sum;
    }
    
    public void setSum(String sum) {
        this.sum = sum;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TSbfjfqkId) ) return false;
		 TSbfjfqkId castOther = ( TSbfjfqkId ) other; 
         
		 return ( (this.getNsrbm()==castOther.getNsrbm()) || ( this.getNsrbm()!=null && castOther.getNsrbm()!=null && this.getNsrbm().equals(castOther.getNsrbm()) ) )
 && ( (this.getNsrMc()==castOther.getNsrMc()) || ( this.getNsrMc()!=null && castOther.getNsrMc()!=null && this.getNsrMc().equals(castOther.getNsrMc()) ) )
 && ( (this.getGljg()==castOther.getGljg()) || ( this.getGljg()!=null && castOther.getGljg()!=null && this.getGljg().equals(castOther.getGljg()) ) )
 && ( (this.getZsxm()==castOther.getZsxm()) || ( this.getZsxm()!=null && castOther.getZsxm()!=null && this.getZsxm().equals(castOther.getZsxm()) ) )
 && ( (this.getZspm()==castOther.getZspm()) || ( this.getZspm()!=null && castOther.getZspm()!=null && this.getZspm().equals(castOther.getZspm()) ) )
 && ( (this.getSbfs()==castOther.getSbfs()) || ( this.getSbfs()!=null && castOther.getSbfs()!=null && this.getSbfs().equals(castOther.getSbfs()) ) )
 && ( (this.getSksx1()==castOther.getSksx1()) || ( this.getSksx1()!=null && castOther.getSksx1()!=null && this.getSksx1().equals(castOther.getSksx1()) ) )
 && ( (this.getSksx2()==castOther.getSksx2()) || ( this.getSksx2()!=null && castOther.getSksx2()!=null && this.getSksx2().equals(castOther.getSksx2()) ) )
 && ( (this.getSfsx()==castOther.getSfsx()) || ( this.getSfsx()!=null && castOther.getSfsx()!=null && this.getSfsx().equals(castOther.getSfsx()) ) )
 && ( (this.getSsq()==castOther.getSsq()) || ( this.getSsq()!=null && castOther.getSsq()!=null && this.getSsq().equals(castOther.getSsq()) ) )
 && ( (this.getSum()==castOther.getSum()) || ( this.getSum()!=null && castOther.getSum()!=null && this.getSum().equals(castOther.getSum()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getNsrbm() == null ? 0 : this.getNsrbm().hashCode() );
         result = 37 * result + ( getNsrMc() == null ? 0 : this.getNsrMc().hashCode() );
         result = 37 * result + ( getGljg() == null ? 0 : this.getGljg().hashCode() );
         result = 37 * result + ( getZsxm() == null ? 0 : this.getZsxm().hashCode() );
         result = 37 * result + ( getZspm() == null ? 0 : this.getZspm().hashCode() );
         result = 37 * result + ( getSbfs() == null ? 0 : this.getSbfs().hashCode() );
         result = 37 * result + ( getSksx1() == null ? 0 : this.getSksx1().hashCode() );
         result = 37 * result + ( getSksx2() == null ? 0 : this.getSksx2().hashCode() );
         result = 37 * result + ( getSfsx() == null ? 0 : this.getSfsx().hashCode() );
         result = 37 * result + ( getSsq() == null ? 0 : this.getSsq().hashCode() );
         result = 37 * result + ( getSum() == null ? 0 : this.getSum().hashCode() );
         return result;
   }   


}


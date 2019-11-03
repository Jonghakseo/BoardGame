package Week4.game.main;

import java.util.ArrayList;

public class Hand extends ArrayList{
//    ArrayList<Card> cardList;
//
//    Hand(ArrayList<Card> hand){
//       this.cardList=hand;
//    }


    // 갯수를 셀 tc 변수들 선언
    int tcSE = 0; int tcSC = 0; int tcSU = 0;int tcRD = 0;int tcMC = 0;
    // 갯수를 셀 bc 변수들 선언
    int bcAP = 0; int bcCP = 0; int  bcOB = 0; int bcST = 0;
    // 갯수를 셀 sc 변수를 선언
    int scAS = 0; int scCP = 0; int scIS = 0; int scLS = 0; int scRM = 0;

    public int getScAS() {
        return scAS;
    }

    public void setScAS(int scAS) {
        this.scAS = scAS;
    }

    public int getScCP() {
        return scCP;
    }

    public void setScCP(int scCP) {
        this.scCP = scCP;
    }

    public int getScIS() {
        return scIS;
    }

    public void setScIS(int scIS) {
        this.scIS = scIS;
    }

    public int getScLS() {
        return scLS;
    }

    public void setScLS(int scLS) {
        this.scLS = scLS;
    }

    public int getScRM() {
        return scRM;
    }

    public void setScRM(int scRM) {
        this.scRM = scRM;
    }

    public int getTcSE() {
        return tcSE;
    }

    public void setTcSE(int tcSE) {
        this.tcSE = tcSE;
    }

    public int getTcSC() {
        return tcSC;
    }

    public void setTcSC(int tcSC) {
        this.tcSC = tcSC;
    }

    public int getTcSU() {
        return tcSU;
    }

    public void setTcSU(int tcSU) {
        this.tcSU = tcSU;
    }

    public int getTcRD() {
        return tcRD;
    }

    public void setTcRD(int tcRD) {
        this.tcRD = tcRD;
    }

    public int getTcMC() {
        return tcMC;
    }

    public void setTcMC(int tcMC) {
        this.tcMC = tcMC;
    }

    public int getBcAP() {
        return bcAP;
    }

    public void setBcAP(int bcAP) {
        this.bcAP = bcAP;
    }

    public int getBcCP() {
        return bcCP;
    }

    public void setBcCP(int bcCP) {
        this.bcCP = bcCP;
    }

    public int getBcOB() {
        return bcOB;
    }

    public void setBcOB(int bcOB) {
        this.bcOB = bcOB;
    }

    public int getBcST() {
        return bcST;
    }

    public void setBcST(int bcST) {
        this.bcST = bcST;
    }

    public ArrayList<Card> toList(){
        return (ArrayList<Card>)this;
    }//그냥 어레이 리스트로 쓰고싶으면 toList로 형 변환
}

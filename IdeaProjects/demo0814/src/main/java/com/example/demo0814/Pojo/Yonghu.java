package com.example.demo0814.Pojo;


public class Yonghu {

  private long id;
  private String xingming;
  private String zhanghao;
  private String mima;
  private long age;
  private long leibie;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getXingming() {
    return xingming;
  }

  public void setXingming(String xingming) {
    this.xingming = xingming;
  }


  public String getZhanghao() {
    return zhanghao;
  }

  public void setZhanghao(String zhanghao) {
    this.zhanghao = zhanghao;
  }


  public String getMima() {
    return mima;
  }

  public void setMima(String mima) {
    this.mima = mima;
  }


  public long getAge() {
    return age;
  }

  public void setAge(long age) {
    this.age = age;
  }


  public long getLeibie() {
    return leibie;
  }

  public void setLeibie(long leibie) {
    this.leibie = leibie;
  }

  @Override
  public String toString() {
    return "Yonghu{" +
            "id=" + id +
            ", xingming='" + xingming + '\'' +
            ", zhanghao='" + zhanghao + '\'' +
            ", mima='" + mima + '\'' +
            ", age=" + age +
            ", leibie=" + leibie +
            '}';
  }
}

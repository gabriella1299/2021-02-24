package it.polito.tdp.PremierLeague.model;

public class Arco {
	Player p1;
	Player p2;
	Double d; //peso
	public Arco(Player p1, Player p2, Double d) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.d = d;
	}
	public Player getP1() {
		return p1;
	}
	public void setP1(Player p1) {
		this.p1 = p1;
	}
	public Player getP2() {
		return p2;
	}
	public void setP2(Player p2) {
		this.p2 = p2;
	}
	public Double getD() {
		return d;
	}
	public void setD(Double d) {
		this.d = d;
	}
	@Override
	public String toString() {
		return "Arco [p1=" + p1 + ", p2=" + p2 + ", d=" + d + "]";
	}
	
	
}

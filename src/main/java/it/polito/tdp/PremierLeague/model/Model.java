package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	private Graph<Player,DefaultWeightedEdge> grafo;
	private Map<Integer,Player> map;
	
	public Model() {
		this.dao=new PremierLeagueDAO();
	}
	
	public List<Match> listAllMatches(){
		return this.dao.listAllMatches();
	}

	public void creaGrafo(Match m) {
		this.grafo=new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.map=new HashMap<>();
		
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(m, map));
		
		List<Arco> archi=this.dao.getArchi(m, map);
		for(Arco a:archi) {
			if(this.grafo.containsVertex(a.getP1()) && this.grafo.containsVertex(a.getP2())) {
				double eff1=this.dao.getEfficienza(a.getP1(), m);
				double eff2=this.dao.getEfficienza(a.getP2(), m);
				double diff=Math.abs(eff1-eff2); //se >0-->eff1>eff2
				a.setD(diff);
				if(eff1>eff2) {
					Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(), a.getD());
				}
				else if(eff2>eff1) {
					Graphs.addEdgeWithVertices(this.grafo, a.getP2(), a.getP1(), a.getD());
				}
				
			}
		}
		
		
	}

	public int  getNVertici() {
		// TODO Auto-generated method stub
		return this.grafo.vertexSet().size();
	}

	public int getNArchi() {
		// TODO Auto-generated method stub
		return this.grafo.edgeSet().size();
	}
	
	public GiocatoreMigliore getGiocatoreMigliore() {
		Player best=null;
		double max =(double) Integer.MIN_VALUE;
		
		
		for(Player p: this.grafo.vertexSet()) {
			
			double pesoUscente = 0.0 ;
			for(DefaultWeightedEdge e: this.grafo.outgoingEdgesOf(p))
				pesoUscente += this.grafo.getEdgeWeight(e) ;
			
			double pesoEntrante = 0.0 ;
			for(DefaultWeightedEdge e: this.grafo.incomingEdgesOf(p))
				pesoEntrante -= this.grafo.getEdgeWeight(e) ;
			
			double delta=pesoUscente-pesoEntrante;
			if(delta>max) {
				best = p ;
				max=delta;
			}
		}
		
		GiocatoreMigliore g=new GiocatoreMigliore(best,max);
		return g; 
		
		
		

	}
}

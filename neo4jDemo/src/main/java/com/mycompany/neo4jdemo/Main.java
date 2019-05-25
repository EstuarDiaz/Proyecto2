/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.neo4jdemo;

import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 *
 * @author DARKTONE
 */
public class Main {
    
    public enum NodeType implements Label{
        Person, Course;
    }
    
    public enum RelationType implements RelationshipType{
        Knows, BelongsTo;
    }
    
    public static void main(String[] args) {
        GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
        GraphDatabaseService graphDb = dbFactory.newEmbeddedDatabase("/opt/neo4j-community-2.1.6/data/graph.db");
        
        try(Transaction tx = (Transaction) graphDb.beginTx()){
            Node bobNode = graphDb.createNode(NodeType.Person);
            bobNode.setProperty("PId", 5001);
            bobNode.setProperty("Name", "Bob");
            bobNode.setProperty("Age", 23);
            
            Node aliceNode = graphDb.createNode(NodeType.Person);
            aliceNode.setProperty("PId", 5002);
            aliceNode.setProperty("Name", "Alice");
            
            Node eveNode = graphDb.createNode(NodeType.Person);
            eveNode.setProperty("Name", "Eve");
            
            Node itNode = graphDb.createNode(NodeType.Course);
            itNode.setProperty("Id", 1);
            itNode.setProperty("Name", "IT Beginners");
            itNode.setProperty("Location", "Room 153");
            
            Node electronicNode = graphDb.createNode(NodeType.Course);
            electronicNode.setProperty("Name", "Electronics Advanced");
            
            bobNode.createRelationshipTo(aliceNode, RelationType.Knows);
            
            Relationship bobRelIt = bobNode.createRelationshipTo(itNode, RelationType.BelongsTo);
            bobRelIt.setProperty("Function", "Student");
            
            Relationship bobRelElectronics = bobNode.createRelationshipTo(electronicNode, RelationType.BelongsTo);
            bobRelElectronics.setProperty("Function", "Supply Teacher");
            
            Relationship aliceRelIt = aliceNode.createRelationshipTo(itNode, RelationType.BelongsTo);
            aliceRelIt.setProperty("Function", "Teacher");
            
            tx.success();
            
        }
        graphDb.shutdown();
    }
    
}

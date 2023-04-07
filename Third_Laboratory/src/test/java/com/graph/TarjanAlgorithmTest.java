package com.graph;

import com.entities.Company;
import com.entities.Person;
import com.entities.Programmer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class TarjanAlgorithmTest {
    @Test
    public void shouldReturnZeroArticulationPoints_whenNetworkContainsZeroNodes() {
        // arrange
        Network network = new Network();
        TarjanAlgorithm tarjanAlgorithm = new TarjanAlgorithm(network);

        // act
        List<Node> articulationPoints = tarjanAlgorithm.getCutPoints();

        // assert
        Assertions.assertTrue(articulationPoints.isEmpty());
    }

    @Test
    public void shouldReturnZeroArticulationPoint_whenNetworkContainsOneNode() {
        // arrange
        Person person1 = new Person("Ioan", new GregorianCalendar(2002, Calendar.MAY, 11).getTime());
        Network network = new Network();
        network.addNode(person1);
        TarjanAlgorithm tarjanAlgorithm = new TarjanAlgorithm(network);

        // act
        List<Node> articulationPoints = tarjanAlgorithm.getCutPoints();

        // assert
        List<Node> expectedArticulationPoints = new ArrayList<>();
        Assertions.assertEquals(expectedArticulationPoints, articulationPoints);
    }

    @Test
    public void shouldReturnOneArticulationPoint_whenNetworkContainsThreeNodesAndTwoEdges() {
        // arrange
        Date sampleBirthDate = new GregorianCalendar(2002, Calendar.MAY, 11).getTime();
        Person person1 = new Programmer("Ioan", sampleBirthDate, 50);
        Person person2 = new Programmer("Alex", sampleBirthDate, 0);
        Company company = new Company("Amazon");
        person1.addRelationships(company, "employer");
        person2.addRelationships(company, "employer");
        Network network = new Network();

        network.addNode(person1);
        network.addNode(person2);
        network.addNode(company);

        TarjanAlgorithm tarjanAlgorithm = new TarjanAlgorithm(network);

        // act
        List<Node> articulationPoints = tarjanAlgorithm.getCutPoints();

        // assert
        List<Node> expectedArticulationPoints = Collections.singletonList(company);
        Assertions.assertEquals(expectedArticulationPoints, articulationPoints);
    }

    @Test
    public void shouldReturnTwoBiconnectedComponents_whenNetworkContainsThreeNodesAndTwoEdges() {
        // arrange
        Date sampleBirthDate = new GregorianCalendar(2002, Calendar.MAY, 11).getTime();
        Person person1 = new Programmer("Ioan", sampleBirthDate, 50);
        Person person2 = new Programmer("Alex", sampleBirthDate, 0);
        Company company = new Company("Amazon");
        person1.addRelationships(company, "employer");
        person2.addRelationships(company, "employer");
        Network network = new Network();

        network.addNode(person1);
        network.addNode(person2);
        network.addNode(company);

        TarjanAlgorithm tarjanAlgorithm = new TarjanAlgorithm(network);

        // act
        List<List<Node>> biconnectedComponents = tarjanAlgorithm.getBiconnectedComponents();

        // assert
        List<List<Node>> expectedBiconnectedComponents = new ArrayList<>();
        expectedBiconnectedComponents.add(Arrays.asList(person2, company));
        expectedBiconnectedComponents.add(Arrays.asList(company, person1));

        Assertions.assertEquals(2, biconnectedComponents.size());
        Assertions.assertEquals(expectedBiconnectedComponents, biconnectedComponents);
    }
}
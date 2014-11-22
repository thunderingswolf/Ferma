/******************************************************************************
 *                                                                             *
 *  Copyright: (c) Syncleus, Inc.                                              *
 *                                                                             *
 *  You may redistribute and modify this source code under the terms and       *
 *  conditions of the Open Source Community License - Type C version 1.0       *
 *  or any later version as published by Syncleus, Inc. at www.syncleus.com.   *
 *  There should be a copy of the license included with this file. If a copy   *
 *  of the license is not included you are granted no right to distribute or   *
 *  otherwise use this file except through a legal and valid license. You      *
 *  should also contact Syncleus, Inc. at the information below if you cannot  *
 *  find a license:                                                            *
 *                                                                             *
 *  Syncleus, Inc.                                                             *
 *  2604 South 12th Street                                                     *
 *  Philadelphia, PA 19148                                                     *
 *                                                                             *
 ******************************************************************************/
package com.syncleus.ferma.annotations;

import com.syncleus.ferma.FramedEdge;
import com.syncleus.ferma.FramedGraph;
import com.syncleus.ferma.FramedVertex;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class IncidenceHandlerTest {
    private static final Set<Class<?>> TEST_TYPES = new HashSet<Class<?>>(Arrays.asList(new Class<?>[]{God.class, FatherEdge.class, GodExtended.class, FatherEdgeExtended.class}));

    @Test
    public void testGetSonEdges() {
        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        final AnnotationTypeResolver resolver = new AnnotationTypeResolver();
        final AnnotationFrameFactory factory = new AnnotationFrameFactory(TEST_TYPES);
        final FramedGraph framedGraph = new FramedGraph(godGraph, factory, resolver);

        final List<God> gods = framedGraph.V().has("name", "jupiter").toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father instanceof FramedVertex);
        final FramedVertex fatherVertex = (FramedVertex) father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final Iterable<? extends FatherEdge> childrenEdges = father.getSonEdges(FatherEdge.class);
        final Iterator<? extends FatherEdge> childEdgeIterator = childrenEdges.iterator();
        Assert.assertTrue(childEdgeIterator.hasNext());
        final FatherEdge childEdge = childEdgeIterator.next();
        Assert.assertTrue(childEdge instanceof FramedEdge);
        final FramedEdge edge = (FramedEdge) childEdge;
        Assert.assertEquals(edge.element().getVertex(Direction.OUT).getProperty("name"), "hercules");
    }

    @Test
    public void testGetSonEdgesExtended() {
        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        final AnnotationTypeResolver resolver = new AnnotationTypeResolver();
        final AnnotationFrameFactory factory = new AnnotationFrameFactory(TEST_TYPES);
        final FramedGraph framedGraph = new FramedGraph(godGraph, factory, resolver);

        final List<God> gods = framedGraph.V().has("name", "jupiter").toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father instanceof FramedVertex);
        final FramedVertex fatherVertex = (FramedVertex) father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final Iterable<? extends FatherEdge> childrenEdges = father.getSonEdges(FatherEdgeExtended.class);
        final Iterator<? extends FatherEdge> childEdgeIterator = childrenEdges.iterator();
        Assert.assertTrue(childEdgeIterator.hasNext());
        final FatherEdge childEdge = childEdgeIterator.next();
        Assert.assertTrue(childEdge instanceof FatherEdgeExtended);
        Assert.assertTrue(childEdge instanceof FramedEdge);
        final FramedEdge edge = (FramedEdge) childEdge;
        Assert.assertEquals(edge.element().getVertex(Direction.OUT).getProperty("name"), "hercules");
    }

    @Test
    public void testGetSonEdge() {
        final TinkerGraph godGraph = new TinkerGraph();
        GodGraphLoader.load(godGraph);

        final AnnotationTypeResolver resolver = new AnnotationTypeResolver();
        final AnnotationFrameFactory factory = new AnnotationFrameFactory(TEST_TYPES);
        final FramedGraph framedGraph = new FramedGraph(godGraph, factory, resolver);

        final List<God> gods = framedGraph.V().has("name", "jupiter").toList(God.class);

        final God father = gods.iterator().next();
        Assert.assertTrue(father instanceof FramedVertex);
        final FramedVertex fatherVertex = (FramedVertex) father;
        Assert.assertEquals(fatherVertex.getProperty("name"), "jupiter");

        final FatherEdge childEdge = father.getSonEdge(FatherEdge.class);
        Assert.assertTrue(childEdge instanceof FramedEdge);
        final FramedEdge edge = (FramedEdge) childEdge;
        Assert.assertEquals(edge.element().getVertex(Direction.OUT).getProperty("name"), "hercules");
    }
}

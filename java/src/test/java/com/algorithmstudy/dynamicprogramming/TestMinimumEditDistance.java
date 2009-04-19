package com.algorithmstudy.dynamicprogramming;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * Test cases for the Minimum Edit Distance algorithm.
 */
public class TestMinimumEditDistance extends TestCase {

  @Test
  public void testAllEqualCostsMED() {
    assertEquals(0, MinimumEditDistance.getAllEqualCostsMED("a", "a"));
    assertEquals(1, MinimumEditDistance.getAllEqualCostsMED("a", "b"));
    assertEquals(1, MinimumEditDistance.getAllEqualCostsMED("b", "a"));
    assertEquals(4, MinimumEditDistance.getAllEqualCostsMED("computation", "computable"));
    assertEquals(7, MinimumEditDistance.getAllEqualCostsMED("computation", "coMPutAbLe"));
    assertEquals(4, MinimumEditDistance.getAllEqualCostsMED("computable", "computation"));
    assertEquals(2, MinimumEditDistance.getAllEqualCostsMED("dish", "mesh"));
    assertEquals(2, MinimumEditDistance.getAllEqualCostsMED("adversary", "adversity"));
    assertEquals(4, MinimumEditDistance.getAllEqualCostsMED("starvation", "observation"));
    assertEquals(6, MinimumEditDistance.getAllEqualCostsMED("interest", "industry"));
  }

  @Test
  public void testSubstitutionNotAllowedMED() {
    assertEquals(0, MinimumEditDistance.getSubstitutionNotAllowedMED("a", "a"));
    assertEquals(2, MinimumEditDistance.getSubstitutionNotAllowedMED("a", "b"));
    assertEquals(2, MinimumEditDistance.getSubstitutionNotAllowedMED("b", "a"));
    assertEquals(7, MinimumEditDistance.getSubstitutionNotAllowedMED("computation", "computable"));
    assertEquals(13, MinimumEditDistance.getSubstitutionNotAllowedMED("computation", "coMPutAbLe"));
    assertEquals(7, MinimumEditDistance.getSubstitutionNotAllowedMED("computable", "computation"));
    assertEquals(4, MinimumEditDistance.getSubstitutionNotAllowedMED("dish", "mesh"));
    assertEquals(4, MinimumEditDistance.getSubstitutionNotAllowedMED("adversary", "adversity"));
    assertEquals(5, MinimumEditDistance.getSubstitutionNotAllowedMED("starvation", "observation"));
    assertEquals(8, MinimumEditDistance.getSubstitutionNotAllowedMED("interest", "industry"));
  }

  public void testAllEqualCostsMEDSpaceOptimized() {
    assertEquals(0, MinimumEditDistance.getAllEqualCostsMEDSpaceOptimized("a", "a"));
    assertEquals(1, MinimumEditDistance.getAllEqualCostsMEDSpaceOptimized("a", "b"));
    assertEquals(1, MinimumEditDistance.getAllEqualCostsMEDSpaceOptimized("b", "a"));
    assertEquals(4, MinimumEditDistance.getAllEqualCostsMEDSpaceOptimized("computation",
        "computable"));
    assertEquals(7, MinimumEditDistance.getAllEqualCostsMEDSpaceOptimized("computation",
        "coMPutAbLe"));
    assertEquals(4, MinimumEditDistance.getAllEqualCostsMEDSpaceOptimized("computable",
        "computation"));
    assertEquals(2, MinimumEditDistance.getAllEqualCostsMEDSpaceOptimized("dish", "mesh"));
    assertEquals(2, MinimumEditDistance.getAllEqualCostsMEDSpaceOptimized("adversary", "adversity"));
    assertEquals(4, MinimumEditDistance.getAllEqualCostsMEDSpaceOptimized("starvation",
        "observation"));
    assertEquals(6, MinimumEditDistance.getAllEqualCostsMEDSpaceOptimized("interest", "industry"));
  }

  @Test
  public void testSubstitutionNotAllowedMEDSpaceOptimized() {
    assertEquals(0, MinimumEditDistance.getSubstitutionNotAllowedMEDSpaceOptimized("a", "a"));
    assertEquals(2, MinimumEditDistance.getSubstitutionNotAllowedMEDSpaceOptimized("a", "b"));
    assertEquals(2, MinimumEditDistance.getSubstitutionNotAllowedMEDSpaceOptimized("b", "a"));
    assertEquals(7, MinimumEditDistance.getSubstitutionNotAllowedMEDSpaceOptimized("computation",
        "computable"));
    assertEquals(13, MinimumEditDistance.getSubstitutionNotAllowedMEDSpaceOptimized("computation",
        "coMPutAbLe"));
    assertEquals(7, MinimumEditDistance.getSubstitutionNotAllowedMEDSpaceOptimized("computable",
        "computation"));
    assertEquals(4, MinimumEditDistance.getSubstitutionNotAllowedMEDSpaceOptimized("dish", "mesh"));
    assertEquals(4, MinimumEditDistance.getSubstitutionNotAllowedMEDSpaceOptimized("adversary",
        "adversity"));
    assertEquals(5, MinimumEditDistance.getSubstitutionNotAllowedMEDSpaceOptimized("starvation",
        "observation"));
    assertEquals(8, MinimumEditDistance.getSubstitutionNotAllowedMEDSpaceOptimized("interest",
        "industry"));
  }

  @Test
  public void testMinimumEditDistanceFailures() {
    try {
      MinimumEditDistance.getAllEqualCostsMED(null, "a");
      assertTrue(false);
    } catch (NullPointerException e) {
    }

    try {
      MinimumEditDistance.getAllEqualCostsMED("a", null);
      assertTrue(false);
    } catch (NullPointerException e) {
    }

    try {
      MinimumEditDistance.getAllEqualCostsMED("", "a");
      assertTrue(false);
    } catch (IllegalArgumentException e) {
    }

    try {
      MinimumEditDistance.getAllEqualCostsMED("a", "");
      assertTrue(false);
    } catch (IllegalArgumentException e) {
    }

    try {
      MinimumEditDistance.getSubstitutionNotAllowedMED(null, "a");
      assertTrue(false);
    } catch (NullPointerException e) {
    }

    try {
      MinimumEditDistance.getSubstitutionNotAllowedMED("a", null);
      assertTrue(false);
    } catch (NullPointerException e) {
    }

    try {
      MinimumEditDistance.getSubstitutionNotAllowedMED("", "a");
      assertTrue(false);
    } catch (IllegalArgumentException e) {
    }

    try {
      MinimumEditDistance.getSubstitutionNotAllowedMED("a", "");
      assertTrue(false);
    } catch (IllegalArgumentException e) {
    }

  }

}

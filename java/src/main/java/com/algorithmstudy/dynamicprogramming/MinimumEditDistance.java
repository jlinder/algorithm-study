package com.algorithmstudy.dynamicprogramming;

/**
 * The Minimum Edit Distance algorithm, also known as the Levenshtein distance algorithm, uses
 * dynamic programming to determine the minimum number of changes to make to transform a source
 * string into a target string. <br>
 * <br>
 * 
 * The time complexity is {@code O(m*n)} where {@code m} is the length of the source string and
 * {@code n} is the length of the target string. If {@code m} and {@code n} grow at approximately
 * the same rate, then time complexity is essentially {@code O(n*n)} (exponential growth). <br>
 * <br>
 * 
 * The space complexity of the basic implementation is {@code O(m*n)} as a two-dimensional array is
 * created to store the computations (see {@code getMED()}. However, the space complexity can be
 * optimized to be {@code O(m)} or {@code O(n)}. Optimizing in space complexity is useful when the
 * edit distance is the desired goal and the ability to trace back through the optimal path is not
 * necessary. {@code getMEDSpaceOptimized()} shows an {@code O(n)} optimized implementation. <br>
 * <br>
 * 
 * Uses of this algorithm include: spell checking, plagiarism detection, molecular biology
 * (measuring the distance between DNA sequences), file revision (the diff program) and speech
 * recognition. <br>
 * <br>
 * 
 * Additional resources:
 * 
 * <ul>
 * <li>
 * V. I. Levenshtein.
 * <em>Binary codes capable of correcting deletions, insertions and reversals</em>. Soviet Physics
 * Doklady 10(8) p707-710, Feb 1966.</li>
 * <li>Christopher D. Manning, Prabhakar Raghavan and Hinrich Schütze,
 * <em>Introduction to Information Retrieval</em>, Cambridge University Press. Chapter 3, Section
 * 3.3.3, "Edit Distance," p58, 2008.</li>
 * </ul>
 */
public class MinimumEditDistance {

  /**
   * Compute the minimum edit distance using a cost structure where insertions, deletions and
   * substitutions all have an equal cost of 1.
   * 
   * @param target
   *          The target string.
   * @param source
   *          The source string.
   * @return The minimum edit distance between the source and target strings according to the
   *         "all operations have equal cost" cost structure.
   */
  public static int getAllEqualCostsMED(String target, String source) {
    checkArgs(target, source);
    return getMED(target, source, OperationCosts.AllEqual);
  }

  /**
   * Compute the minimum edit distance using a cost structure where insertions and deletions have a
   * cost of 1 and substitutions have a cost of 2 effectively making substitutions "not allowed".
   * 
   * @param target
   *          The target string.
   * @param source
   *          The source string.
   * @return The minimum edit distance between the source and target strings according to the
   *         "substitutions not allowed" cost structure.
   */
  public static int getSubstitutionNotAllowedMED(String target, String source) {
    checkArgs(target, source);
    return getMED(target, source, OperationCosts.NoSubstitution);
  }

  /**
   * Compute the minimum edit distance using a space-optimized implementation and a cost structure
   * where insertions, deletions and substitutions all have an equal cost of 1.
   * 
   * @param target
   *          The target string.
   * @param source
   *          The source string.
   * @return The minimum edit distance between the source and target strings according to the
   *         "all operations have equal cost" cost structure.
   */
  public static int getAllEqualCostsMEDSpaceOptimized(String target, String source) {
    checkArgs(target, source);
    return getMEDSpaceOptimized(target, source, OperationCosts.AllEqual);
  }

  /**
   * Compute the minimum edit distance using a space-optimized implementation and a cost structure
   * where insertions and deletions have a cost of 1 and substitutions have a cost of 2 effectively
   * making substitutions "not allowed".
   * 
   * @param target
   *          The target string.
   * @param source
   *          The source string.
   * @return The minimum edit distance between the source and target strings according to the
   *         "substitutions not allowed" cost structure.
   */
  public static int getSubstitutionNotAllowedMEDSpaceOptimized(String target, String source) {
    checkArgs(target, source);
    return getMEDSpaceOptimized(target, source, OperationCosts.NoSubstitution);
  }

  /**
   * An implementation of the basic minimum edit distance algorithm. The full {@code m} by {@code n}
   * matrix is created and thus the space complexity is {@code O(m*n)}.
   * 
   * @param target
   *          The target string.
   * @param source
   *          The source string.
   * @param costs
   *          A cost structure to use when computing the costs.
   * @return The minimum edit distance between the source and target strings according to the
   *         specified cost structure.
   */
  private static int getMED(String target, String source, OperationCosts costs) {
    int tLen = target.length();
    int sLen = source.length();

    int[][] distance = new int[tLen + 1][sLen + 1];

    distance[0][0] = 0;

    // m assignments are made (linear growth)
    for (int i = 1; i <= tLen; i++) {
      distance[i][0] = distance[i - 1][0] + costs.insertionCost();
    }

    // n assignments are made (linear growth)
    for (int j = 1; j <= sLen; j++) {
      distance[0][j] = distance[0][j - 1] + costs.deletionCost();
    }

    // m * n operations are made (exponential growth)
    for (int i = 1; i <= tLen; i++) {
      for (int j = 1; j <= sLen; j++) {
        // one operation each for insertion, deletion and substitution; done m*n times
        int dInsert = distance[i - 1][j] + costs.insertionCost();
        int dDelete = distance[i][j - 1] + costs.deletionCost();
        int dSub = distance[i - 1][j - 1]
            + costs.substitutionCost(target.charAt(i - 1), source.charAt(j - 1));

        // two comparisons and a single assignment are always made; done m*n times
        if (dInsert < dDelete) {
          if (dInsert < dSub) {
            distance[i][j] = dInsert;
          } else {
            distance[i][j] = dSub;
          }
        } else if (dDelete < dSub) {
          distance[i][j] = dDelete;
        } else {
          distance[i][j] = dSub;
        }
      }
    }

    return distance[tLen][sLen];
  }

  /**
   * An implementation of a space-optimized minimum edit distance algorithm. An optimized sized
   * matrix is used of size {@code 2*n} (versus a full {@code m} by {@code n} matrix) resulting in a
   * space complexity {@code O(n)}.
   * 
   * @param target
   *          The target string.
   * @param source
   *          The source string.
   * @param costs
   *          A cost structure to use when computing the costs.
   * @return The minimum edit distance between the source and target strings according to the
   *         specified cost structure.
   */
  private static int getMEDSpaceOptimized(String target, String source, OperationCosts costs) {
    int tLen = target.length();
    int sLen = source.length();

    int[][] distance = new int[2][sLen + 1];

    distance[0][0] = 0;
    distance[1][0] = distance[0][0] + costs.insertionCost();

    // n assignments are made (linear growth)
    for (int j = 1; j <= sLen; j++) {
      distance[0][j] = distance[0][j - 1] + costs.deletionCost();
    }

    for (int i = 1; i <= tLen; i++) {
      int r = (i % 2 == 0 ? 0 : 1);
      int notR = (0 == r ? 1 : 0);

      // m assignments are made (linear growth)
      distance[r][0] = i;

      for (int j = 1; j <= sLen; j++) {
        /*
         * one operation each for insertion, deletion and substitution; done m*n times (exponential
         * growth)
         */
        int dInsert = distance[notR][j] + costs.insertionCost();
        int dDelete = distance[r][j - 1] + costs.deletionCost();
        int dSub = distance[notR][j - 1]
            + costs.substitutionCost(target.charAt(i - 1), source.charAt(j - 1));

        /*
         * two comparisons and a single assignment are always made; done m*n times (exponential
         * growth)
         */
        if (dInsert < dDelete) {
          if (dInsert < dSub) {
            distance[r][j] = dInsert;
          } else {
            distance[r][j] = dSub;
          }
        } else if (dDelete < dSub) {
          distance[r][j] = dDelete;
        } else {
          distance[r][j] = dSub;
        }
      }
    }

    int r = (tLen % 2 == 0 ? 0 : 1);
    return distance[r][sLen];
  }

  private enum OperationCosts {
    AllEqual(1, 1, 1), NoSubstitution(1, 1, 2);

    private int insertion;
    private int deletion;
    private int substitution;

    private OperationCosts(int insertion, int deletion, int substitution) {
      this.insertion = insertion;
      this.deletion = deletion;
      this.substitution = substitution;
    }

    public int insertionCost() {
      return insertion;
    }

    public int deletionCost() {
      return deletion;
    }

    public int substitutionCost(char c1, char c2) {
      if (c1 == c2) {
        return 0;
      }
      return substitution;
    }
  }

  /**
   * Checks the argument strings to ensure they are valid.
   * 
   * @param target
   *          The target.
   * @param source
   *          The source.
   */
  private static void checkArgs(String target, String source) {
    if (null == target) {
      throw new NullPointerException("Target string required; can't be null.");
    }

    if (null == source) {
      throw new NullPointerException("Source string required; can't be null.");
    }

    if (target.length() == 0) {
      throw new IllegalArgumentException("Target string required; can't be empty.");
    }

    if (source.length() == 0) {
      throw new IllegalArgumentException("Source string required; can't be empty.");
    }
  }

}

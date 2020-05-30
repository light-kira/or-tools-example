package linear;

import com.google.ortools.sat.*;

/**
 * Created by manu.sharma on 5/29/20
 * https://developers.google.com/optimization/cp/cryptarithmetic
 */

public class CryptArithematicPuzzle {
    static {
        System.loadLibrary("jniortools");
    }

    public static void main(String[] args) {
        int base = 10;
        CpModel model = new CpModel();

        IntVar c = model.newIntVar(1, base - 1, "C");
        IntVar p = model.newIntVar(0, base - 1, "P");
        IntVar i = model.newIntVar(1, base - 1, "I");
        IntVar s = model.newIntVar(0, base - 1, "S");
        IntVar f = model.newIntVar(1, base - 1, "F");
        IntVar u = model.newIntVar(0, base - 1, "U");
        IntVar n = model.newIntVar(0, base - 1, "N");
        IntVar t = model.newIntVar(1, base - 1, "T");
        IntVar r = model.newIntVar(0, base - 1, "R");
        IntVar e = model.newIntVar(0, base - 1, "E");

        // We need to group variables in a list to use the constraint AllDifferent.
        IntVar[] letters = new IntVar[] {c, p, i, s, f, u, n, t, r, e};

        // Define constraints.
        model.addAllDifferent(letters);

        // CP + IS + FUN = TRUE
        model.addEquality(LinearExpr.scalProd(new IntVar[] {c, p, i, s, f, u, n, t, r, u, e}, new long[] {base, 1, base, 1, base * base, base, 1, -base * base * base,
                        -base * base, -base, -1}), 0);

        CpSolver cpSolver = new CpSolver();
        VarArraySolutionPrinter cb = new VarArraySolutionPrinter(letters);
        cpSolver.searchAllSolutions(model, cb);
    }

    static class VarArraySolutionPrinter extends CpSolverSolutionCallback {
        public VarArraySolutionPrinter(IntVar[] variables) {
            variableArray = variables;
        }

        @Override
        public void onSolutionCallback() {
            for (IntVar v : variableArray) {
                System.out.printf("  %s = %d", v.getName(), value(v));
            }
            System.out.println();
            solutionCount++;
        }

        public int getSolutionCount() {
            return solutionCount;
        }

        private int solutionCount;
        private final IntVar[] variableArray;
    }
}

package linear;

import com.google.ortools.sat.*;

/**
 * Created by manu.sharma on 5/29/20
 * https://developers.google.com/optimization/cp/cp_solver
 */

public class CPExample {
    static {
        System.loadLibrary("jniortools");
    }

    public static void main(String[] args) {
        CpModel model = new CpModel();

        int numVals = 3;
        IntVar x = model.newIntVar(0, numVals - 1, "x");
        IntVar y = model.newIntVar(0, numVals - 1, "y");
        IntVar z = model.newIntVar(0, numVals - 1, "z");

        model.addDifferent(x, y);

        CpSolver solver = new CpSolver();
        VarArraySolutionPrinter cb = new VarArraySolutionPrinter(new IntVar[]{x, y, z});
        //CpSolverStatus status = solver.solve(model);
        solver.searchAllSolutions(model, cb);
//        if (status == CpSolverStatus.FEASIBLE) {
//            System.out.println("x = " + solver.value(x));
//            System.out.println("y = " + solver.value(y));
//            System.out.println("z = " + solver.value(z));
//        }
    }

    static class VarArraySolutionPrinter extends CpSolverSolutionCallback {
        private int solutionCount;
        private IntVar[] variableArray;

        public VarArraySolutionPrinter(IntVar[] variables){
            this.variableArray = variables;
        }

        @Override
        public void onSolutionCallback(){
            System.out.printf("Solution #%d: time = %.02f s%n", solutionCount, wallTime());
            for (IntVar v : variableArray) {
                System.out.printf("  %s = %d%n", v.getName(), value(v));
            }
            solutionCount++;
        }

        public int getSolutionCount() {
            return solutionCount;
        }
    }
}

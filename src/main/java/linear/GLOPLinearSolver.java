package linear;

/**
 * Created by manu.sharma on 5/29/20
 */

import com.google.ortools.linearsolver.MPConstraint;
import com.google.ortools.linearsolver.MPObjective;
import com.google.ortools.linearsolver.MPSolver;
import com.google.ortools.linearsolver.MPVariable;

/**
 * Objective : Maximize 3 * x + 4 * y
 * Constraints
 * x + 2y	≤	14
    3x – y	≥	0
    x – y	≤	2
 */
public class GLOPLinearSolver {
    static {
        System.loadLibrary("jniortools");
    }

    public static void main(String[] args) throws Exception {
        MPSolver solver = new MPSolver("LinearProgrammingExample", MPSolver.OptimizationProblemType.GLOP_LINEAR_PROGRAMMING);

        double infinity = Double.POSITIVE_INFINITY;
        MPVariable x = solver.makeNumVar(0.0, infinity, "x");
        MPVariable y = solver.makeNumVar(0.0, infinity, "y");

        System.out.println("Number of variables = "+solver.numVariables());

        // x + 2*y <= 14.
        MPConstraint c0 = solver.makeConstraint(-infinity, 14.0, "c0");
        c0.setCoefficient(x, 1);
        c0.setCoefficient(y, 2);

        // 3x – y ≥ 0
        MPConstraint c1 = solver.makeConstraint(0, infinity, "c1");
        c1.setCoefficient(x, 3);
        c1.setCoefficient(y, -1);

        // x – y ≤ 2
        MPConstraint c2 = solver.makeConstraint(-infinity, 2, "c2");
        c2.setCoefficient(x, 1);
        c2.setCoefficient(y, -1);

        System.out.println("Number of constraints = "+solver.numConstraints());

        MPObjective objective = solver.objective();
        objective.setCoefficient(x, 3);
        objective.setCoefficient(y, 4);
        objective.setMaximization();

        final MPSolver.ResultStatus resultStatus = solver.solve();

        if(resultStatus != MPSolver.ResultStatus.OPTIMAL){
            System.err.println("The problem does not have an optimal solution!");
            return;
        }

        // The value of each variable in the solution.
        System.out.println("Solution");
        System.out.println("x = " + x.solutionValue());
        System.out.println("y = " + y.solutionValue());

        // The objective value of the solution.
        System.out.println("Optimal objective value = " + solver.objective().value());
    }
}

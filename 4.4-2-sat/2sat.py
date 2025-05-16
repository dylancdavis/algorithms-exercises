import sys
import numpy as np
import random

largest_var = 0

if len(sys.argv) != 2:
    print("Usage: python 2sat.py <data_file>")
    sys.exit(1)

data_file = sys.argv[1]

variables_clauses = {}

# Read the file
with open(data_file, "r") as f:
    num_clauses = int(f.readline())
    print("Num clauses: ", num_clauses)
    clauses = np.ndarray((num_clauses, 2), dtype=np.int64)

    for index, line in enumerate(f):
        clause_vars = line.split(" ")
        var1 = int(clause_vars[0])
        var2 = int(clause_vars[1])

        clauses[index][0] = var1
        clauses[index][1] = var2

        var1 = abs(var1)
        var2 = abs(var2)

        if var1 > largest_var:
            largest_var = var1
        if var2 > largest_var:
            largest_var = var2

        if var1 - 1 not in variables_clauses:
            variables_clauses[var1 - 1] = [index]
        else:
            variables_clauses[var1 - 1].append(index)

        if var2 - 1 not in variables_clauses:
            variables_clauses[var2 - 1] = [index]
        else:
            variables_clauses[var2 - 1].append(index)

num_variables = largest_var

# Generate random variable assigments
variable_assignments = np.zeros((num_variables))
for i in range(num_variables):
    if random.random() < 0.5:
        variable_assignments[i] = True
    else:
        variable_assignments[i] = False

# Build initial list of unsatisfied clauses
unsatisfied_clauses = set()
for i in range(num_clauses):
    [var1, var2] = clauses[i]
    var1_idx = abs(var1) - 1
    var2_idx = abs(var2) - 1

    val1 = variable_assignments[var1_idx]
    val2 = variable_assignments[var2_idx]

    if var1 < 0:
        val1 = not val1
    if var2 < 0:
        val2 = not val2

    is_satisfied = val1 or val2

    if not is_satisfied:
        unsatisfied_clauses.add(i)

print("Number of starting unsatisfied clauses:", len(unsatisfied_clauses))


inner_loop_limit = 2 * (num_variables**2)
print(f"Using loop limit: {inner_loop_limit}")

# single inner loop
for i in range(inner_loop_limit):
    num_unsatisfied = len(unsatisfied_clauses)
    if i % 100_000 == 0:
        print(f"Iteration {i}: {num_unsatisfied} clauses left")
    if num_unsatisfied == 0:
        print("Satisfiable assignment identified.")
        break

    clause_to_fix = clauses[unsatisfied_clauses.pop()]
    [var1, var2] = clause_to_fix

    var1_idx = abs(var1) - 1
    var2_idx = abs(var2) - 1

    if random.random() < 0.5:
        selected_index = var1_idx
    else:
        selected_index = var2_idx

    variable_assignments[selected_index] = not variable_assignments[selected_index]
    for clause_index in variables_clauses[selected_index]:
        # TODO recalculate other clauses
        [var1, var2] = clauses[clause_index]
        var1_idx = abs(var1) - 1
        var2_idx = abs(var2) - 1
        val1 = variable_assignments[var1_idx]
        val2 = variable_assignments[var2_idx]
        if var1 < 0:
            val1 = not val1
        if var2 < 0:
            val2 = not val2
        if val1 or val2:
            if clause_index in unsatisfied_clauses:
                unsatisfied_clauses.remove(clause_index)
        else:
            unsatisfied_clauses.add(clause_index)

# Exited loop. Verify all clauses...
print("Finished iterations. Checking all clauses...")
is_satisfied = True
for i in range(num_clauses):
    [var1, var2] = clauses[i]
    var1_idx = abs(var1) - 1
    var2_idx = abs(var2) - 1

    val1 = variable_assignments[var1_idx]
    val2 = variable_assignments[var2_idx]

    if var1 < 0:
        val1 = not val1
    if var2 < 0:
        val2 = not val2

    if not (val1 or val2):
        is_satisfied = False
        print("Unsatisfied Clause: ", i)
        break

if is_satisfied:
    print("Satisfying assignment found.")
else:
    print("No satisfying assignment found.")

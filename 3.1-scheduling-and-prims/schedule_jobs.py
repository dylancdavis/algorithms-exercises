import sys

input_file = sys.argv[1]

# If false, use difference for weighted value
USE_RATIO = False

jobs = []
with open(input_file) as f:
    for line in f:
        (weight, length) = tuple(map(int, line.split()))
        if USE_RATIO:
            weighted_value = weight / length
        else:
            weighted_value = weight - length
        jobs.append((weight, length, weighted_value))

# sort by weight first, then ratio or difference
sorted_jobs = sorted(jobs, key=lambda j: j[0], reverse=True)
sorted_jobs = sorted(sorted_jobs, key=lambda j: j[2], reverse=True)

total_time = 0
weighted_sum = 0

for job in sorted_jobs:
    (weight, length, _) = job
    total_time += length
    weighted_sum += (weight * total_time)

print(weighted_sum)

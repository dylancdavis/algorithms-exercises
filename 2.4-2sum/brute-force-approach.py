import sys

input_file = sys.argv[1]

# both bounds will be treated as inclusive
upper_bound = 10000
lower_bound = -upper_bound

# Parse input
with open(input_file) as f:
    nums = [int(line) for line in f]

found_nums = 0


for k in range(lower_bound, upper_bound + 1):
    # print(f"Checking k: {k}")
    diffs = set()  # store diffs to search for
    for n in nums:
        if n in diffs:
            print(f"Found k: {k}")
            print()
            found_nums += 1
            break
        diff = k - n
        if not diff == n:  # check distinct
            diffs.add(diff)

print(found_nums)

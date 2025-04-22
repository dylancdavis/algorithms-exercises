import matplotlib.pyplot as plt

# Path to the file with space-delimited pairs
data_file = 'tsp.txt'

# Lists to store x and y values
x_values = []
y_values = []

# Read the file
with open(data_file, 'r') as file:
    for line in file:
        try:
            x, y = map(float, line.split())  # Split by space and convert to float
            x_values.append(x)
            y_values.append(y)
        except ValueError:
            print(f"Skipping invalid line: {line.strip()}")

# Plot the data as a scatterplot
plt.figure(figsize=(8, 6))
plt.scatter(x_values, y_values, color='b', label='Data')
plt.title('X-Y Scatter Plot')
plt.xlabel('X')
plt.ylabel('Y')
plt.legend()
plt.grid(True)

# Save the plot to a PNG file
plt.savefig('scatter_plot.png')

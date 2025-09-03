class KnapsackSimple {

    public static void main(String[] args) {
        String[] names = {"Laptop", "Headphones", "Jacket", "Camera", "Book", "Shoes"};
        int[] weights = {3, 1, 5, 4, 2, 6};
        int[] values  = {9, 5, 10, 7, 4, 6};

        int n = names.length;
        int capacity = 15;

        int[][] dp = new int[n + 1][capacity + 1];


        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= capacity; j++) {
            
                dp[i][j] = dp[i - 1][j];

            
                if (weights[i - 1] <= j) {
                    int val = dp[i - 1][j - weights[i - 1]] + values[i - 1];
                    if (val > dp[i][j]) {
                        dp[i][j] = val;
                    }
                }
            }
        }

       
        int w = capacity;
        boolean[] chosen = new boolean[n]; 

        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i - 1][w]) { 
                chosen[i - 1] = true;
                w -= weights[i - 1];
            }
        }

        
        int totalWeight = 0, totalValue = 0;
        System.out.println("Optimal selection for capacity = " + capacity + " kg:");
        for (int i = 0; i < n; i++) {
            if (chosen[i]) {
                System.out.println("- " + names[i] + " (w=" + weights[i] + " kg, v=" + values[i] + ")");
                totalWeight += weights[i];
                totalValue += values[i];
            }
        }
        System.out.println("Total weight: " + totalWeight + " kg");
        System.out.println("Total value: " + totalValue);
    }
}

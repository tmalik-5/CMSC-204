public class ProductSalesTracker {
    private double[] sales;
    private int salesSize;

    public ProductSalesTracker(int capacity) {
        sales = new double[capacity];
        salesSize = 0;
    }

    // add one sale if there is room
    public boolean addSale(double sale) {
        if (salesSize == sales.length) {
            return false; // full
        }
        sales[salesSize] = sale;
        salesSize++;
        return true;
    }

    // total of all recorded sales
    public double totalSales() {
        double sum = 0;
        int i = 0;
        while (i < salesSize) {
            sum += sales[i];
            i++;
        }
        return sum;
    }

    // smallest sale, or 0 if none
    public double lowestSale() {
        if (salesSize == 0) {
            return 0;
        }
        double lowest = sales[0];
        int i = 1;
        while (i < salesSize) {
            if (sales[i] < lowest) {
                lowest = sales[i];
            }
            i++;
        }
        return lowest;
    }

    // total minus the lowest one (needs at least 2 numbers)
    public double finalSalesTotal() {
        if (salesSize < 2) {
            return 0;
        }
        return totalSales() - lowestSale();
    }

    @Override
    public String toString() {
        if (salesSize == 0) {
            return "";
        }
        String result = "" + sales[0];
        for (int i = 1; i < salesSize; i++) {
            result += " " + sales[i];
        }
        return result;
    }
}
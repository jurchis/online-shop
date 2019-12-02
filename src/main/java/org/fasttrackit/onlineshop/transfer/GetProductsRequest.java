package org.fasttrackit.onlineshop.transfer;

public class GetProductsRequest {

    private String partialName;

    //Integer instead of int and default value if nothing in the method will be Null instead of 0
    private Integer minimumQuantity;

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    public Integer getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Integer minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    @Override
    public String toString() {
        return "GetProductsRequest{" +
                "partialName='" + partialName + '\'' +
                ", minimumQuantity=" + minimumQuantity +
                '}';
    }
}

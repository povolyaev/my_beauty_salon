package Models;

import java.util.Objects;

public class ServiceType {
    private int serviceTypeId;
    private String serviceTypeName;

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }

    public void setServiceTypeName(String serviceTypeName) {
        this.serviceTypeName = serviceTypeName;
    }

    public ServiceType(int serviceTypeId, String serviceTypeName) {
        this.serviceTypeId = serviceTypeId;
        this.serviceTypeName = serviceTypeName;
    }

    public ServiceType() {}

    @Override
    public String toString() {
        return "Service type(id = " + getServiceTypeId() + "), name = " + getServiceTypeName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ServiceType serviceType = (ServiceType) o;
        if (serviceTypeId != serviceType.serviceTypeId) {
            return false;
        }
        return serviceTypeName.equals(serviceType.serviceTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceTypeId,serviceTypeName);
    }
}

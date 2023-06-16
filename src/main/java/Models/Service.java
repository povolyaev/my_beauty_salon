package Models;



import java.time.LocalDate;
import java.util.Objects;

public class Service {
    private int serviceId;
    private int clientId;
    private LocalDate serviceDate;
    private ServiceType serviceType;

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Service(int serviceId, int clientId, LocalDate serviceDate, ServiceType serviceType) {
        this.serviceId = serviceId;
        this.clientId = clientId;
        this.serviceDate = serviceDate;
        this.serviceType = serviceType;
    }

    @Override
    public String toString() {
        return serviceDate + " был назеачен сеанс на процедуру - " + serviceType.getServiceTypeName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Service service = (Service) o;
        if (serviceId != service.serviceId) {
            return false;
        }
        if (clientId != service.clientId) {
            return false;
        }
        if (!serviceDate.equals(service.serviceDate)) {
            return false;
        }
        return serviceType.equals(service.serviceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, clientId, serviceDate);
    }
}

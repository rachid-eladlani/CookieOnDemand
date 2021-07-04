package fr.unice.polytech.cookieFactory.external_api;

public class DeliveryApp {


    public DeliveryApp(){

    }
    /**
     * @description Livraison simple via MarcelEat
     * @param delivery
     * @return boolean
     */
    public String simpleDelivery(Delivery delivery){
        if(isSimpleDelivery(delivery)){
            if(isDeliveryAvailable(delivery)) {
                return "Confirmation simple : OK";
            }
            else return "Confirmation simple: NOK";
        }
        return "Please use last minute delivery for this order.";

    }

    private boolean isSimpleDelivery(Delivery delivery) {
        return delivery != null;
    }

    /**
     * @description Prix de la course taxes incluses
     * @param delivery
     * @return
     */
    public double estimateOrderWithMarcelEatDelivery(Delivery delivery){
        return delivery.getOrder().getFinalAmount() * (50.0f/100.0f);
    }

    /**
     * @description Demande de livraison de dernière minute avec MarcelEat
     * @param delivery
     * @return
     */
    public String lastMinuteDelivery(Delivery delivery) {
        if (isDeliveryAvailable(delivery)) {
            delivery.getOrder().newPriceWithDelivery(estimateOrderWithMarcelEatDelivery(delivery));
            return "Confirmation last-minute: OK";
        } else
            return "Confirmation last-minute: NOK";

    }


    private boolean isDeliveryAvailable(Delivery delivery) {
        return delivery != null;
    }

    public boolean delivered(Delivery delivery){
        return true;
    }

}

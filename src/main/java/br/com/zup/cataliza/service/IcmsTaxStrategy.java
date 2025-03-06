package br.com.zup.cataliza.service;

public class IcmsTaxStrategy implements TaxCalculationStrategy{
    @Override
    public double calculateTax(double value) {
        return 0.18 * value ;
    }
}

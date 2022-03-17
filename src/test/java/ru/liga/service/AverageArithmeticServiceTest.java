//package ru.liga.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import ru.liga.model.Currency;
//import ru.liga.model.Rate;
//import ru.liga.repository.InMemoryRatesRepository;
//import ru.liga.repository.RatesRepository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static ru.liga.RateTestData.RATE;
//import static ru.liga.RateTestData.RATES;
//
//class AverageArithmeticServiceTest {
//
//    RatesRepository repository = new InMemoryRatesRepository();
//    ForecastService service = new AverageArithmeticService(repository);
//
//    @BeforeEach
//    void setUp() {
//        repository.getAllRates(Currency.EUR).addAll(0, RATES);
//    }
//
//    @Test
//    void getDayRate() {
//        Rate actual = service.getDayRate(Currency.EUR);
//        assertThat(actual).isEqualTo(RATE);
//    }
//
//}
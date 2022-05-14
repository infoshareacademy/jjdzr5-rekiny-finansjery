package com.infoshareacademy.presentationlayer.search;

import com.infoshareacademy.presentationlayer.Menu;
import com.infoshareacademy.services.DailyExchangeRatesSearchService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;

@ExtendWith(MockitoExtension.class)
class SearchUITest {

    private static InputStream stdIn;
    @Mock
    private DailyExchangeRatesSearchService dailyExchangeRatesSearchService;
    @Mock
    private Menu menu;

    @BeforeAll
    static void beforeAll() {
        String data = "0\r\n";
        stdIn = System.in;
        System.setIn(new ByteArrayInputStream(data.getBytes()));
    }

    @AfterAll
    static void afterAll() {
        System.setIn(stdIn);
    }

    @Test
    void runMenu_doesNothing_dueToNegativeFlag() {
        SearchUI searchUI = new SearchUI(dailyExchangeRatesSearchService, menu, false);
        assertDoesNotThrow(searchUI::runMenu);
    }

    @Test
    void runMenu_iteratesThroughMenuOnce() {
        // given
        SearchUI searchUI = new SearchUI(dailyExchangeRatesSearchService, menu, true);
        given(menu.getMenuSize()).willReturn(1);
        // when
        assertThrows(NoSuchElementException.class, searchUI::runMenu);
        // then
        then(menu).should(atLeastOnce()).displayMenu();
        then(menu).should().executeSelectedOption(0);
    }
}

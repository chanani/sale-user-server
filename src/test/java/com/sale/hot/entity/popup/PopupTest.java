package com.sale.hot.entity.popup;

import com.sale.hot.domain.popup.repository.PopupRepository;
import com.sale.hot.entity.common.constant.BooleanYn;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PopupTest {

    @Autowired
    PopupRepository popupRepository;

    @Test
    public void sNames() throws Exception {
        Popup popup = Popup.builder()
                .title("팝업 타이틀")
                .content("팝업 내용")
                .active(BooleanYn.N)
                .build();

        Popup save = popupRepository.save(popup);

        System.out.println("save = " + save.getId());


    }

}
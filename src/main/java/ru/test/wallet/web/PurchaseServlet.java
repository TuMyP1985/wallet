package ru.test.wallet.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.test.wallet.model.Purchase;
import ru.test.wallet.repository.PurchaseRepository;
import ru.test.wallet.repository.inmemory.InMemoryPurchaseRepository;
import ru.test.wallet.util.PurchasesUtil;
import ru.test.wallet.web.purchase.PurchaseRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.test.wallet.repository.inmemory.InMemoryPurchaseRepository.*;
import static ru.test.wallet.util.PurchasesUtil.DEFAULT_PRICE_LIMIT_DAY;

public class PurchaseServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(PurchaseServlet.class);

    private PurchaseRepository repository;
    private ConfigurableApplicationContext appCtx;
    private PurchaseRestController сontroller;
    private Integer idUserTest = 1;
    private boolean idEx = true;

    @Override
    public void init() {
        repository = new InMemoryPurchaseRepository();
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        сontroller = appCtx.getBean(PurchaseRestController.class);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("Esc") != null) {
            log.info("getAll");
            Collection<Purchase> purchases = сontroller.getAll().stream().map(m -> (createFromTo(m, idUserTest))).collect(Collectors.toList());
            request.setAttribute("purchases", PurchasesUtil.getTos(purchases, PurchasesUtil.DEFAULT_PRICE_LIMIT_DAY));
            request.getRequestDispatcher("/purchases.jsp").forward(request, response);
            return;
        } else if (request.getParameter("Filtr") != null)
        {
            log.info("getAll filter");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");

            Collection<Purchase> purchases = сontroller.getAllFilter(startDate.isEmpty()?null:LocalDate.parse(startDate),
                    startTime.isEmpty()?null: LocalTime.parse(startTime),
                    endDate.isEmpty()?null:LocalDate.parse(endDate),
                    endTime.isEmpty()?null:LocalTime.parse(endTime)
            ).stream().map(m -> (createFromTo(m, idUserTest))).collect(Collectors.toList());
            request.setAttribute("purchases",PurchasesUtil.getTos(purchases, DEFAULT_PRICE_LIMIT_DAY));
            request.setAttribute("startDate",startDate);
            request.setAttribute("endDate",endDate);
            request.setAttribute("startTime",startTime);
            request.setAttribute("endTime",endTime);
            request.getRequestDispatcher("/purchases.jsp").forward(request, response);
//            getAllFilter

            return;
        }


            request.setCharacterEncoding("UTF-8");
            String id = request.getParameter("id");
            Integer userId = SecurityUtil.authUserId();

        Purchase purchase = new Purchase(id.isEmpty() ? null : Integer.valueOf(id),
                    SecurityUtil.authUserId(),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("product"),
                    Integer.parseInt(request.getParameter("price")));

            log.info(purchase.isNew() ? "Create {}" : "Update {}", purchase);
            if (purchase.isNew())
                сontroller.create(createTo(purchase, idEx));
            else
                сontroller.update(createTo(purchase, idEx), purchase.getId());

//        repository.save(purchase, userId);
            response.sendRedirect("purchases");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        Integer userId = SecurityUtil.authUserId();


        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
//                repository.delete(id, userId);                                                               //repository
                сontroller.delete(id);
                response.sendRedirect("purchases");
                break;
            case "create":
            case "update":
                final Purchase purchase = "create".equals(action) ?
                        new Purchase(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
//                        repository.get(getId(request), userId);                                                 //repository
                        createFromTo(сontroller.get(getId(request)), idUserTest);
                request.setAttribute("purchase", purchase);
                request.getRequestDispatcher("/purchaseForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("purchases",
//                        PurchasesUtil.getTos(repository.getAll(userId), PurchasesUtil.DEFAULT_PRICE_LIMIT_DAY));       //repository
                        PurchasesUtil.getTos(сontroller.getAll().stream().map(m -> (createFromTo(m, idUserTest))).collect(Collectors.toList()),
                                PurchasesUtil.DEFAULT_PRICE_LIMIT_DAY));
//                request.setAttribute("startDate",);
//                request.setAttribute("endDate",endDate);
//                request.setAttribute("startTime",startTime);
//                request.setAttribute("endTime",endTime);
                request.getRequestDispatcher("/purchases.jsp").forward(request, response);
                break;
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        appCtx.close();

    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}

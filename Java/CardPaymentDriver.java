// Suppose you are imlementing a payment gateway, where it is
// necessary to check if the card provided by the buyer has been
// blocked (e.g. because the owner has reported its loss). An efficient
// way to perform perform this operation is using a HashSet, so that when a
// card is blocked is introduced into the collection. When it is
// necessary to process a payment, the first operation should be to
// check if the collection contains contains the card.

// This program creates these Java classes: Card,
// PaymentGateway, and PaymentGatewayTester with a
// static main method to test and show the behavior of the previous
// classes. The PaymentGateway class should implement a method to
// introduce a card in the bloqued-cards collection and another
// method to check if a Card is blocked or not.

import java.util.HashSet;

public class CardPaymentDriver {
    public static void main(String[] args) {
        PaymentGatewayTester tester = new PaymentGatewayTester();
        tester.testExplicitCards();
        tester.testImplicitCards();
    }
}

class Card {
    private int number;

    public Card(int number) {
        this.number = number;
    }
    public int getNumber() {
        return this.number;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {return false;}
        if (this == o) {return true;}

        if ((o instanceof Card) && (((Card) o).getNumber() == this.number)) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public int hashCode() {
        int result = 0;
        result = (int) result/11;
        return result;
    }
}

class PaymentGateway {
    private HashSet<Card> cards = new HashSet<Card>();
    private HashSet<Card> blockedCards = new HashSet<Card>();

    public boolean addCard(Card card) {
        // Returns false if card is already added
        return cards.add(card);
    }
    public boolean blockCard(Card card) {
        // Returns false if card is already blocked or not part of cards
        return cards.contains(card) ? blockedCards.add(card) : false;
    }
    public boolean isBlocked(Card card) {
        return blockedCards.contains(card);
    }
}

class PaymentGatewayTester {
    public void testExplicitCards() {
        PaymentGateway payGate = new PaymentGateway();
        System.out.println("Testing explicitly declared cards:");

        // Initiate cards
        Card a = new Card(0);
        Card b = new Card(1);
        Card c = new Card(2);
        Card d = new Card(3);
    
        System.out.println(payGate.blockCard(b)); // expected : false (not part of cards yet)
    
        // Add cards
        payGate.addCard(a);
        payGate.addCard(b);
        payGate.addCard(c);
        payGate.addCard(d);
    
        System.out.println(payGate.addCard(b));   // expected: false (already added)
        System.out.println(payGate.blockCard(b)); // expected: true  (part of cards)
        System.out.println(payGate.blockCard(b)); // expected: false (already blocked)
        System.out.println(payGate.isBlocked(b)); // expected: true
        System.out.println(payGate.isBlocked(a)); // expected: false    
    }
    public void testImplicitCards() {
        PaymentGateway payGate = new PaymentGateway();
        System.out.println("Testing cards implicitly declared over a loop:");

        // Add cards from 1 to 500
        for (int i = 1; i <= 500; i++) {
            payGate.addCard(new Card(i));
        }

        System.out.println(payGate.addCard(new Card(2)));   // expected: false (already added)
        System.out.println(payGate.blockCard(new Card(2))); // expected: true  (part of cards)
        System.out.println(payGate.blockCard(new Card(2))); // expected: false (already blocked)
        System.out.println(payGate.isBlocked(new Card(2))); // expected: true
        System.out.println(payGate.isBlocked(new Card(1))); // expected: false
    }
}

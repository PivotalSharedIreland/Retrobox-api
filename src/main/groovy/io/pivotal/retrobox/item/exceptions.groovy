package io.pivotal.retrobox.item

class ItemNotFoundException extends RuntimeException{
    ItemNotFoundException(String s) {
        super(s)
    }
}

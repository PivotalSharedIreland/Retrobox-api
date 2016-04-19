package io.pivotal.retrobox

class ItemNotFoundException extends RuntimeException{
    ItemNotFoundException(String s) {
        super(s)
    }
}
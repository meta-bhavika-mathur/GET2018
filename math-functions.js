const sum = (a, b = "") => {
    if(b == "") {
        return function(b) {
            return a + b;
        }
    }
    else
        return a + b;
}

export {sum};

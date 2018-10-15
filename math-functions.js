/* A function sum which will do the sum for input parameters when called as: sum(x,y) or sum(x)(y) */
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

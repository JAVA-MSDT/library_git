

export const fetchFunction = async(url) => {
    let resultholder;
    await fetch(url)
    .then(Response => Response.json())
    .then(result => resultholder = result);
    return resultholder;
}
import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token : string | null = localStorage.getItem("token");

  const newreq = req.clone({
    setHeaders : {
      Authorization : `Bearer ${token}`
    }
  })
  console.log(newreq)
  return next(newreq);
};

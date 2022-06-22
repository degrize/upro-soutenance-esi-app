import { Directive, EventEmitter, HostBinding, HostListener, Input, Output } from '@angular/core';
import { Eleve } from '../../../enumerations/eleve';

export type SortColumn = keyof Eleve | '';
export type SortDirection = 'asc' | 'desc' | '';
const rotate: { [key: string]: SortDirection } = { asc: 'desc', desc: '', '': 'asc' };

export interface SortEvent {
  column: SortColumn;
  direction: SortDirection;
}

@Directive({
  selector: 'th[jhiSortable]',
})
export class NgbdSortableHeaderDirective {
  @Input() sortable: SortColumn = '';
  @Input() direction: SortDirection = '';
  @Output() sort = new EventEmitter<SortEvent>();

  @HostBinding('class.asc') asc = 'direction === "asc"';
  @HostBinding('class.desc') desc = 'direction === "desc"';

  @HostListener('click') rotate(): void {
    this.direction = rotate[this.direction];
    this.sort.emit({ column: this.sortable, direction: this.direction });
  }
}

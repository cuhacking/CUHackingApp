class CreateEvents < ActiveRecord::Migration[5.1]
  def change
    create_table :events do |t|
      t.has_one :room
      
      t.date :date
      t.time :start_time
      t.time :end_time
      t.string :type
      t.string :description

      t.timestamps
    end
  end
end
